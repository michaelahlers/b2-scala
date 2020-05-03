package ahlers.b2.api.v2

import org.scalatest.wordspec._
import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class PlayJsonFormatsSpec extends AnyWordSpec with VerifyJsonEncodings {

  import PlayJsonFormats._
  import PlayJsonFormatsSpec._

//it must "serialize list buckets" in {
//  import ScalaCheckPropertyChecks._
//  import ScalacheckShapeless._
//
//  Inspectors.forAll {
//    Resource.my.getAsStream("list-buckets-request_0.json").autoClosed(Json.parse) ::
//      Nil
//  } { verifyFormat[ListBuckets] }
//
//  forAll(verifyFormat(_: ListBuckets))
//}
//
//it must "serialize bucket list" in {
//  import ScalaCheckPropertyChecks._
//  import ScalacheckShapeless._
//
//  Inspectors.forAll {
//    Resource.my.getAsStream("list-buckets-response_0.json").autoClosed(Json.parse) ::
//      Nil
//  } { verifyFormat[BucketList] }
//
//  forAll(verifyFormat(_: BucketList))
//}
//
//it must "serialize operation" in {
//  Inspectors.forAll(Operation.values) {
//    verifyFormat(_)
//  }
//}

  implicit override def EncodingAccountAuthorization = FormatEncoding[AccountAuthorization]
  implicit override def EncodingCorsRule = FormatEncoding[CorsRule]
  implicit override def EncodingLifecycleRule = FormatEncoding[LifecycleRule]

}

object PlayJsonFormatsSpec {

  import VerifyJsonEncodings._

  class FormatEncoding[A: Format] extends Encoding[A] {
    override def read = Json.parse(_).as[A]
    override def write = (Json.toJson(_: A)).andThen(Json.stringify _)
    override def iterable = FormatEncoding[Iterable[A]]
  }

  object FormatEncoding {
    def apply[A: Format]: Encoding[A] = new FormatEncoding[A]
  }

}
