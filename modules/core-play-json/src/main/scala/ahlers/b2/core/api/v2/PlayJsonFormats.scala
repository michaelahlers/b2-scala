package ahlers.b2.core.api.v2

import io.lemonlabs.uri._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object PlayJsonFormats extends PlayJsonFormats

trait PlayJsonFormats {

  implicit def FormatAuthorization: Format[Authorization] = {
    import Authorization._
    import Allowed._
    import Capability._

    implicit val ReadsCapability: Reads[Capability] =
      implicitly[Reads[String]].reads(_) flatMap {
        case "listKeys" => JsSuccess(ListKeys)
        case "writeKeys" => JsSuccess(WriteKeys)
        case "deleteKeys" => JsSuccess(DeleteKeys)
        case "listBuckets" => JsSuccess(ListBuckets)
        case "writeBuckets" => JsSuccess(WriteBuckets)
        case "deleteBuckets" => JsSuccess(DeleteBuckets)
        case "listFiles" => JsSuccess(ListFiles)
        case "readFiles" => JsSuccess(ReadFiles)
        case "shareFiles" => JsSuccess(ShareFiles)
        case "writeFiles" => JsSuccess(WriteFiles)
        case "deleteFiles" => JsSuccess(DeleteFiles)
        case x => JsError(JsonValidationError("error.capability.unknown", x))
      }

    implicit val WritesCapability: Writes[Capability] =
      implicitly[Writes[String]] contramap[Capability] {
        case ListKeys => "listKeys"
        case WriteKeys => "writeKeys"
        case DeleteKeys => "deleteKeys"
        case ListBuckets => "listBuckets"
        case WriteBuckets => "writeBuckets"
        case DeleteBuckets => "deleteBuckets"
        case ListFiles => "listFiles"
        case ReadFiles => "readFiles"
        case ShareFiles => "shareFiles"
        case WriteFiles => "writeFiles"
        case DeleteFiles => "deleteFiles"
      }

    implicit def FormatUrl: Format[Url] = ???

    implicit val FormatAccount:Format[Account] =
      (__ \ "accountId").format[String].inmap(Account, _.id)

    implicit val FormatBucket: Format[Bucket] =
      ((__ \ "bucketId").format[String] and
        (__ \ "bucketName").format[String])
        .apply(Bucket.apply, unlift(Bucket.unapply))

    implicit val FormatAllowed: Format[Allowed] =
      ((__).formatNullable[Bucket] and
        (__ \ "capabilities").format[Seq[Capability]] and
        (__ \ "namePrefix").formatNullable[String])
        .apply(Allowed.apply, unlift(Allowed.unapply))

    implicit val FormatPartSizes:Format[PartSizes] =
      ((__ \ "absoluteMinimumPartSize").format[Long] and
        (__ \ "recommendedPartSize").format[Long])
      .apply(PartSizes.apply, unlift(PartSizes.unapply))

    ((__).format[Account] and
      (__).format[Allowed] and
      (__ \ "authorizationToken").format[Token] and
      (__).format[PartSizes] and
      (__ \ "apiUrl").format[Url] and
      (__ \ "downloadUrl").format[Url])
      .apply(Authorization.apply, unlift(Authorization.unapply))
  }

}
