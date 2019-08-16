package ahlers.b2.api.v2

import io.lemonlabs.uri._
import play.api.libs.json._

import scala.util._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object PlayJsonFormats extends PlayJsonFormats

trait PlayJsonFormats {

  implicit def ReadsAuthorization: Reads[Authorization] = {
    import Authorization._
    import Allowed._
    import Capability._
    import Json._

    implicit val ReadsCapability: Reads[Capability] =
      implicitly[Reads[String]].reads(_) flatMap {
        case "listKeys"      => JsSuccess(ListKeys)
        case "writeKeys"     => JsSuccess(WriteKeys)
        case "deleteKeys"    => JsSuccess(DeleteKeys)
        case "listBuckets"   => JsSuccess(ListBuckets)
        case "writeBuckets"  => JsSuccess(WriteBuckets)
        case "deleteBuckets" => JsSuccess(DeleteBuckets)
        case "listFiles"     => JsSuccess(ListFiles)
        case "readFiles"     => JsSuccess(ReadFiles)
        case "shareFiles"    => JsSuccess(ShareFiles)
        case "writeFiles"    => JsSuccess(WriteFiles)
        case "deleteFiles"   => JsSuccess(DeleteFiles)
        case x               => JsError(JsonValidationError("error.authorization.capability.unknown", x))
      }

    implicit val ReadsAllowedJson: Reads[AuthorizationAllowedJson] = reads[AuthorizationAllowedJson]

    reads[AuthorizationJson] map { x =>
      Authorization(
        account = Account(x.accountId),
        allowed = Allowed(
          bucket = x.allowed.bucketId.map(Bucket(_, x.allowed.bucketName)),
          capabilities = x.allowed.capabilities,
          namePrefix = x.allowed.namePrefix
        ),
        token = x.authorizationToken,
        partSizes = PartSizes(x.absoluteMinimumPartSize, x.recommendedPartSize),
        service = x.apiUrl,
        download = x.downloadUrl
      )
    }
  }

  implicit def WritesAuthorization: Writes[Authorization] = {
    import Capability._
    import Json._

    implicit val WritesCapability: Writes[Capability] =
      implicitly[Writes[String]] contramap {
        case ListKeys      => "listKeys"
        case WriteKeys     => "writeKeys"
        case DeleteKeys    => "deleteKeys"
        case ListBuckets   => "listBuckets"
        case WriteBuckets  => "writeBuckets"
        case DeleteBuckets => "deleteBuckets"
        case ListFiles     => "listFiles"
        case ReadFiles     => "readFiles"
        case ShareFiles    => "shareFiles"
        case WriteFiles    => "writeFiles"
        case DeleteFiles   => "deleteFiles"
      }

    implicit val WritesAllowedJson: Writes[AuthorizationAllowedJson] = writes[AuthorizationAllowedJson]

    writes[AuthorizationJson] contramap { x =>
      AuthorizationJson(
        absoluteMinimumPartSize = x.partSizes.minimum,
        accountId = x.account.id,
        allowed = AuthorizationAllowedJson(
          bucketId = x.allowed.bucket.map(_.id),
          bucketName = x.allowed.bucket.flatMap(_.name),
          capabilities = x.allowed.capabilities,
          namePrefix = x.allowed.namePrefix
        ),
        apiUrl = x.service,
        authorizationToken = x.token,
        downloadUrl = x.download,
        recommendedPartSize = x.partSizes.recommended
      )
    }
  }

  implicit val ReadsScalaUrl: Reads[Url] = {
    implicitly[Reads[String]].reads(_) flatMap { x =>
      Url.parseTry(x) match {
        case Success(x) => JsSuccess(x)
        case Failure(reason) =>
          JsError {
            Option(reason)
              .map(_.getMessage)
              .map(JsonValidationError("error.expected.url", x, _))
              .getOrElse(JsonValidationError("error.expected.url", x))
          }
      }
    }
  }

  implicit val WritesScalaUrl: Writes[Url] =
    implicitly[Writes[String]].contramap(_.toString)

}

private case class AuthorizationAllowedJson(
    bucketId: Option[String],
    bucketName: Option[String],
    capabilities: Seq[Capability],
    namePrefix: Option[String]
)

private case class AuthorizationJson(
    absoluteMinimumPartSize: Long,
    accountId: String,
    allowed: AuthorizationAllowedJson,
    apiUrl: Url,
    authorizationToken: String,
    downloadUrl: Url,
    recommendedPartSize: Long
)
