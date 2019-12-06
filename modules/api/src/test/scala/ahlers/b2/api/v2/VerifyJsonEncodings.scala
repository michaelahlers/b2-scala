package ahlers.b2.api.v2

import better.files._
import cats.syntax.option._
import com.softwaremill.diffx.scalatest.DiffMatcher._
import org.scalacheck._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._
import org.scalatestplus.scalacheck._
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait VerifyJsonEncodings[F[_]] {
  this: AnyWordSpecLike =>

  import VerifyJsonEncodings._

  implicit def EncodingAccountAuthorization: Encoding[AccountAuthorization]

  "Json Encodings" must {

    "read and write account authorizations" in {
      import AccountAuthorization._
      import Capability._

      Encoding[AccountAuthorization].read(Resource.my.getAsString("authorize-account-response_0.json")) should matchTo(
        AccountAuthorization(
          5000000,
          "YOUR_ACCOUNT_ID",
          Allowed(
            Seq(ListBuckets, ListFiles, ReadFiles, ShareFiles, WriteFiles, DeleteFiles),
            "BUCKET_ID".some,
            "BUCKET_NAME".some,
            none
          ),
          "https://apiNNN.backblazeb2.com",
          "4_0022623512fc8f80000000001_0186e431_d18d02_acct_tH7VW03boebOXayIc43-sxptpfA=",
          "https://f002.backblazeb2.com",
          100000000
        )
      )

      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      forAll { accountAuthorization: AccountAuthorization =>
        ((__ \ "absoluteMinimumPartSize").read[Long] and
          (__ \ "accountId").read[String] and
          (__ \ "allowed").read(
            ((__ \ "capabilities").read(Reads.seq(Reads[Capability] {
              case JsString("deleteBuckets") => JsSuccess(DeleteBuckets)
              case JsString("deleteFiles")   => JsSuccess(DeleteFiles)
              case JsString("deleteKeys")    => JsSuccess(DeleteKeys)
              case JsString("listBuckets")   => JsSuccess(ListBuckets)
              case JsString("listFiles")     => JsSuccess(ListFiles)
              case JsString("listKeys")      => JsSuccess(ListKeys)
              case JsString("readFiles")     => JsSuccess(ReadFiles)
              case JsString("shareFiles")    => JsSuccess(ShareFiles)
              case JsString("writeBuckets")  => JsSuccess(WriteBuckets)
              case JsString("writeFiles")    => JsSuccess(WriteFiles)
              case JsString("writeKeys")     => JsSuccess(WriteKeys)
              case capability                => JsError(s"""Unknown capability "$capability".""")
            })) and
              (__ \ "bucketId").readNullable[String] and
              (__ \ "bucketName").readNullable[String] and
              (__ \ "namePrefix").readNullable[String])
              .apply(Allowed.apply _)
          ) and
          (__ \ "apiUrl").read[String] and
          (__ \ "authorizationToken").read[String] and
          (__ \ "downloadUrl").read[String] and
          (__ \ "recommendedPartSize").read[Long])
          .apply(AccountAuthorization.apply _)
          .reads(Json.parse(Encoding[AccountAuthorization].write(accountAuthorization)))
          .should(matchTo(JsSuccess(accountAuthorization): JsResult[AccountAuthorization]))
      }
    }

  }

  //def serializeBucketTypes(implicit A: Encoding)

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
  }

  object Encoding {
    def apply[A: Encoding]: Encoding[A] = implicitly[Encoding[A]]
  }

}
