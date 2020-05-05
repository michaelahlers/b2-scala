package ahlers.b2.api.v2

import ahlers.b2.api.v2.ScalaCheckArbitraryInstances._
import better.files._
import cats.syntax.option._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import eu.timepit.refined.api._
import eu.timepit.refined.auto._
import eu.timepit.refined._
import org.scalacheck._
import org.scalatest.LoneElement._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._
import org.scalatestplus.scalacheck._
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
 * Standard tests against JSON encodings, checking compliance against version 2 of the B2 API.
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait SpecJsonEncodings {
  this: AnyWordSpecLike =>

  type Encoding[A] = SpecJsonEncodings.Encoding[A]
  val Encoding = SpecJsonEncodings.Encoding

  implicit def EncodingAccountAuthorization: Encoding[AccountAuthorization]
  implicit def EncodingCorsRule: Encoding[CorsRule]
  implicit def EncodingLifecycleRule: Encoding[LifecycleRule]

  "JSON Encodings" must {

    implicit def ReadsRefined[T, P, F[_, _]](implicit T: Reads[T], F: RefType[F], TP: Validate[T, P]): Reads[F[T, P]] =
      T.flatMap(F.refine(_).fold(Reads.failed(_), Reads.pure(_)))

    implicit val ReadsAccountId: Reads[AccountId] = Reads.of[String Refined collection.NonEmpty].map(AccountId(_))

    implicit val ReadsAuthorizationToken: Reads[AuthorizationToken] = Reads.of[String Refined collection.NonEmpty].map(AuthorizationToken(_))

    implicit val ReadsBucketId: Reads[BucketId] = Reads.of[String Refined collection.NonEmpty].map(BucketId(_))
    implicit val ReadsBucketName: Reads[BucketName] = Reads.of[String Refined collection.NonEmpty].map(BucketName(_))
    implicit val ReadsBucketNamePrefix: Reads[BucketNamePrefix] = Reads.of[String Refined collection.NonEmpty].map(BucketNamePrefix(_))

    implicit val ReadsBucketType: Reads[BucketType] = {
      import BucketType._
      Reads[BucketType] {
        case JsString("all") => JsSuccess(All)
        case JsString("allPrivate") => JsSuccess(AllPrivate)
        case JsString("allPublic") => JsSuccess(AllPublic)
        case JsString("snapshot") => JsSuccess(Snapshot)
        case bucketType => JsError(s"""Unknown bucket type "$bucketType".""")
      }
    }

    implicit val ReadsCapability: Reads[Capability] = {
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

    implicit val ReadsOperation: Reads[Operation] = {
      import Operation._
      Reads[Operation] {
        case JsString("b2_download_file_by_name") => JsSuccess(DownloadFileByName)
        case JsString("b2_download_file_by_id") => JsSuccess(DownloadFileById)
        case JsString("b2_upload_file") => JsSuccess(UploadFile)
        case JsString("b2_upload_part") => JsSuccess(UploadPart)
        case operation => JsError(s"""Unknown operation "$operation".""")
      }
    }

    implicit val ReadsPartSize: Reads[PartSize] = Reads.of[Int Refined numeric.NonNegative].map(PartSize(_))

    implicit val ReadsApiUrl: Reads[ApiUrl] = Reads.of[String Refined collection.NonEmpty].map(ApiUrl(_))
    implicit val ReadsDownloadUrl: Reads[DownloadUrl] = Reads.of[String Refined collection.NonEmpty].map(DownloadUrl(_))

    "read and write account authorizations" in {
      import AccountAuthorization._
      import Capability._

      Encoding[AccountAuthorization]
        .read(Resource.my.getAsString("authorize-account-response_0.json"))
        .should(matchTo(AccountAuthorization(
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
        )))

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

      implicit val ReadsCorsRuleName: Reads[CorsRuleName] = Reads.of[String Refined collection.NonEmpty].map(CorsRuleName(_))
      implicit val ReadsAllowedOrigin: Reads[AllowedOrigin] = Reads.of[String Refined collection.NonEmpty].map(AllowedOrigin(_))
      implicit val ReadsAllowedHeader: Reads[AllowedHeader] = Reads.of[String Refined collection.NonEmpty].map(AllowedHeader(_))
      implicit val ReadsExposeHeader: Reads[ExposeHeader] = Reads.of[String Refined collection.NonEmpty].map(ExposeHeader(_))
      implicit val ReadsMaxAgeSeconds: Reads[MaxAgeSeconds] = Reads.of[Int Refined numeric.NonNegative].map(MaxAgeSeconds(_))

      Encoding[CorsRule].iterable
        .read(Resource.my.getAsString("cors-rules_0.json"))
        .loneElement
        .should(matchTo(CorsRule(
          CorsRuleName("downloadFromAnyOrigin"),
          Seq(AllowedOrigin("https")),
          Seq(DownloadFileById, DownloadFileByName),
          Seq(AllowedHeader("range")).some,
          Seq(ExposeHeader("x-bz-content-sha1")).some,
          MaxAgeSeconds(3600)
        )))

      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      forAll { corsRule: CorsRule =>
        (__ \ "corsRuleName")
          .read[CorsRuleName]
          .and((__ \ "allowedOrigins").read[Seq[AllowedOrigin]])
          .and((__ \ "allowedOperations").read[Seq[Operation]])
          .and((__ \ "allowedHeaders").readNullable[Seq[AllowedHeader]])
          .and((__ \ "exposeHeaders").readNullable[Seq[ExposeHeader]])
          .and((__ \ "maxAgeSeconds").read[MaxAgeSeconds])
          .apply(CorsRule.apply _)
          .reads(Json.parse(Encoding[CorsRule].write(corsRule)))
          .get
          .should(matchTo(corsRule))
      }

    }

    "read and write lifecycle rules" in {
      implicit val ReadsDaysFromHidingToDeleting: Reads[DaysFromHidingToDeleting] = Reads.of[Int Refined numeric.NonNegative].map(DaysFromHidingToDeleting(_))
      implicit val ReadsDaysFromUploadingToDeleting: Reads[DaysFromUploadingToDeleting] = Reads.of[Int Refined numeric.NonNegative].map(DaysFromUploadingToDeleting(_))
      implicit val ReadsFileNamePrefix: Reads[FileNamePrefix] = Reads.of[String].map(FileNamePrefix(_))

      Encoding[LifecycleRule]
        .read(Resource.my.getAsString("lifecycle-rule_0.json"))
        .should(
          matchTo(
            LifecycleRule(
              DaysFromHidingToDeleting(30).some,
              none,
              FileNamePrefix("backup/")
            )))

      Encoding[LifecycleRule]
        .read(Resource.my.getAsString("lifecycle-rule_1.json"))
        .should(
          matchTo(
            LifecycleRule(
              DaysFromHidingToDeleting(1).some,
              DaysFromUploadingToDeleting(7).some,
              FileNamePrefix("logs/")
            )))

      Encoding[LifecycleRule]
        .read(Resource.my.getAsString("lifecycle-rule_2.json"))
        .should(
          matchTo(
            LifecycleRule(
              DaysFromHidingToDeleting(1).some,
              none,
              FileNamePrefix("")
            )))

      Encoding[LifecycleRule]
        .read(Resource.my.getAsString("lifecycle-rule_3.json"))
        .should(
          matchTo(
            LifecycleRule(
              DaysFromHidingToDeleting(30).some,
              none,
              FileNamePrefix("")
            )))

      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      forAll { lifecycleRule: LifecycleRule =>
        (__ \ "daysFromHidingToDeleting")
          .readNullable[DaysFromHidingToDeleting]
          .and((__ \ "daysFromUploadingToHiding").readNullable[DaysFromUploadingToDeleting])
          .and((__ \ "fileNamePrefix").read[FileNamePrefix])
          .apply(LifecycleRule.apply _)
          .reads(Json.parse(Encoding[LifecycleRule].write(lifecycleRule)))
          .get
          .should(matchTo(lifecycleRule))
      }

    }

  }

}

object SpecJsonEncodings {

  trait Encoding[A] {
    def read: String => A
    def write: A => String
    def iterable: Encoding[Iterable[A]]
  }

  object Encoding {
    def apply[A: Encoding]: Encoding[A] = implicitly[Encoding[A]]
  }

}
