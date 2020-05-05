package ahlers.b2.api.v2

import better.files._
import cats.syntax.option._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import eu.timepit.refined.api._
import eu.timepit.refined.auto._
import org.scalacheck._
import org.scalatest.LoneElement._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._
import org.scalatestplus.scalacheck._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import ahlers.b2.api.v2.ScalaCheckArbitraryInstances._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait VerifyJsonEncodings {
  this: AnyWordSpecLike =>

  type Encoding[A] = VerifyJsonEncodings.Encoding[A]
  val Encoding = VerifyJsonEncodings.Encoding

  implicit def EncodingAccountAuthorization: Encoding[AccountAuthorization]
  implicit def EncodingCorsRule: Encoding[CorsRule]
  implicit def EncodingLifecycleRule: Encoding[LifecycleRule]

  "JSON Encodings" must {

    implicit def ReadsRefined[T, P, F[_, _]](implicit T: Reads[T], F: RefType[F], TP: Validate[T, P]): Reads[F[T, P]] =
      T.flatMap(F.refine(_).fold(Reads.failed(_), Reads.pure(_)))

    implicit val ReadsAccountId: Reads[AccountId] = AccountId.deriving

    implicit val ReadsAuthorizationToken: Reads[AuthorizationToken] = AuthorizationToken.deriving

    implicit val ReadsBucketId: Reads[BucketId] = BucketId.deriving
    implicit val ReadsBucketName: Reads[BucketName] = BucketName.deriving
    implicit val ReadsBucketNamePrefix: Reads[BucketNamePrefix] = BucketNamePrefix.deriving

    implicit val ReadsBucketType = {
      import BucketType._
      Reads[BucketType] {
        case JsString("all") => JsSuccess(All)
        case JsString("allPrivate") => JsSuccess(AllPrivate)
        case JsString("allPublic") => JsSuccess(AllPublic)
        case JsString("snapshot") => JsSuccess(Snapshot)
        case bucketType => JsError(s"""Unknown bucket type "$bucketType".""")
      }
    }

    implicit val ReadsCapability = {
      import Capability._
      Reads[Capability] {
        case JsString("deleteBuckets") => JsSuccess(DeleteBuckets)
        case JsString("deleteFiles") => JsSuccess(DeleteFiles)
        case JsString("deleteKeys") => JsSuccess(DeleteKeys)
        case JsString("listBuckets") => JsSuccess(ListBuckets)
        case JsString("listFiles") => JsSuccess(ListFiles)
        case JsString("listKeys") => JsSuccess(ListKeys)
        case JsString("readFiles") => JsSuccess(ReadFiles)
        case JsString("shareFiles") => JsSuccess(ShareFiles)
        case JsString("writeBuckets") => JsSuccess(WriteBuckets)
        case JsString("writeFiles") => JsSuccess(WriteFiles)
        case JsString("writeKeys") => JsSuccess(WriteKeys)
        case capability => JsError(s"""Unknown capability "$capability".""")
      }
    }

    implicit val ReadsOperation = {
      import Operation._
      Reads[Operation] {
        case JsString("b2_download_file_by_name") => JsSuccess(DownloadFileByName)
        case JsString("b2_download_file_by_id") => JsSuccess(DownloadFileById)
        case JsString("b2_upload_file") => JsSuccess(UploadFile)
        case JsString("b2_upload_part") => JsSuccess(UploadPart)
        case operation => JsError(s"""Unknown operation "$operation".""")
      }
    }

    implicit val ReadsPartSize: Reads[PartSize] = PartSize.deriving

    implicit val ReadsApiUrl: Reads[ApiUrl] = ApiUrl.deriving
    implicit val ReadsDownloadUrl: Reads[DownloadUrl] = DownloadUrl.deriving

    "read and write account authorizations" in {
      import AccountAuthorization._
      import Capability._

      Encoding[AccountAuthorization].read(Resource.my.getAsString("authorize-account-response_0.json")) should matchTo {
        AccountAuthorization(
          PartSize(5000000),
          AccountId("YOUR_ACCOUNT_ID"),
          Allowed(
            Seq(ListBuckets, ListFiles, ReadFiles, ShareFiles, WriteFiles, DeleteFiles),
            BucketId("BUCKET_ID").some,
            BucketName("BUCKET_NAME").some,
            none
          ),
          ApiUrl("https://apiNNN.backblazeb2.com"),
          AuthorizationToken("4_0022623512fc8f80000000001_0186e431_d18d02_acct_tH7VW03boebOXayIc43-sxptpfA="),
          DownloadUrl("https://f002.backblazeb2.com"),
          PartSize(100000000)
        )
      }

      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      forAll { accountAuthorization: AccountAuthorization =>
        (__ \ "absoluteMinimumPartSize")
          .read[PartSize]
          .and((__ \ "accountId").read[AccountId])
          .and(
            (__ \ "allowed").read(
              (__ \ "capabilities")
                .read[Seq[Capability]]
                .and((__ \ "bucketId").readNullable[BucketId])
                .and((__ \ "bucketName").readNullable[BucketName])
                .and((__ \ "namePrefix").readNullable[BucketNamePrefix])
                .apply(Allowed.apply _)
            ))
          .and((__ \ "apiUrl").read[ApiUrl])
          .and((__ \ "authorizationToken").read[AuthorizationToken])
          .and((__ \ "downloadUrl").read[DownloadUrl])
          .and((__ \ "recommendedPartSize").read[PartSize])
          .apply(AccountAuthorization.apply _)
          .reads(Json.parse(Encoding[AccountAuthorization].write(accountAuthorization)))
          .get
          .should(matchTo(accountAuthorization))
      }
    }

    "read and write CORS rules" in {
      import Operation._

      Encoding[CorsRule].iterable.read(Resource.my.getAsString("cors-rules_0.json")).loneElement should matchTo {
        CorsRule(
          "downloadFromAnyOrigin",
          Seq("https"),
          Seq(DownloadFileById, DownloadFileByName),
          Seq("range").some,
          Seq("x-bz-content-sha1").some,
          3600
        )
      }

      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      forAll { corsRule: CorsRule =>
        (__ \ "corsRuleName")
          .read[String]
          .and((__ \ "allowedOrigins").read[Seq[String]])
          .and((__ \ "allowedOperations").read[Seq[Operation]])
          .and((__ \ "allowedHeaders").readNullable[Seq[String]])
          .and((__ \ "exposeHeaders").readNullable[Seq[String]])
          .and((__ \ "maxAgeSeconds").read[Int])
          .apply(CorsRule.apply _)
          .reads(Json.parse(Encoding[CorsRule].write(corsRule)))
          .get
          .should(matchTo(corsRule))
      }

    }

    "read and write lifecycle rules" in {
      Encoding[LifecycleRule].read(Resource.my.getAsString("lifecycle-rule_0.json")) should matchTo {
        LifecycleRule(
          30.some,
          none,
          "backup/"
        )
      }

      Encoding[LifecycleRule].read(Resource.my.getAsString("lifecycle-rule_1.json")) should matchTo {
        LifecycleRule(
          1.some,
          7.some,
          "logs/"
        )
      }

      Encoding[LifecycleRule].read(Resource.my.getAsString("lifecycle-rule_2.json")) should matchTo {
        LifecycleRule(
          1.some,
          none,
          ""
        )
      }

      Encoding[LifecycleRule].read(Resource.my.getAsString("lifecycle-rule_3.json")) should matchTo {
        LifecycleRule(
          30.some,
          none,
          ""
        )
      }

      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      forAll { lifecycleRule: LifecycleRule =>
        (__ \ "daysFromHidingToDeleting")
          .readNullable[Int]
          .and((__ \ "daysFromUploadingToHiding").readNullable[Int])
          .and((__ \ "fileNamePrefix").read[String])
          .apply(LifecycleRule.apply _)
          .reads(Json.parse(Encoding[LifecycleRule].write(lifecycleRule)))
          .get
          .should(matchTo(lifecycleRule))
      }

    }

  }

}

//case class Formatted[A](x: A, y: JsValue) {
//  def withShape(f: (A, JsValue) => Assertion)(implicit pos: Position): Assertion = f(x, y)
//  def withResult[B: Reads](f: (A, JsResult[B]) => Assertion)(implicit pos: Position): Assertion = f(x, y.validate[B])
//}
//
//def verifyFormat[A: Format](x: A)(implicit pos: Position) = {
//  import Json._
//  inside(toJson(x)) {
//    case y =>
//      y.as[A] should equal(x)
//      Formatted(x, toJson(x))
//  }
//}

//def verifyFormat[A: Format](y: JsValue)(implicit pos: Position) = {
//  import Json._
//  inside(y.validate[A]) {
//    case JsSuccess(x, _) =>
//      (toJson(x) should equal(y))(after being nullsRemoved)
//      Formatted(x, y)
//  }
//
//}

object VerifyJsonEncodings {

  trait Encoding[A] {
    def read: String => A
    def write: A => String
    def iterable: Encoding[Iterable[A]]
  }

  object Encoding {
    def apply[A: Encoding]: Encoding[A] = implicitly[Encoding[A]]
  }

}
