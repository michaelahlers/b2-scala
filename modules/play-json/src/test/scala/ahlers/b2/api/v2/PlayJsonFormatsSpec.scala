package ahlers.b2.api.v2

import better.files._
import cats.Functor
import org.scalacheck._
import org.scalactic._
import org.scalactic.source._
import org.scalatest.flatspec._
import org.scalatest.Inside._
import org.scalatest._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.enablers._
import org.scalatest.wordspec._
import org.scalatestplus.scalacheck._
import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class PlayJsonFormatsSpec extends AnyWordSpec with VerifyJsonEncoding[Format] {

  import PlayJsonFormats._
  import PlayJsonFormatsSpec._

//it must "serialize CORS rule" in {
//  import ScalaCheckPropertyChecks._
//  import ScalacheckShapeless._
//
//  Inspectors.forAll {
//    Resource.my.getAsStream("cors-rules_0.json").autoClosed(Json.parse(_).as[Seq[JsValue]])
//  } { verifyFormat[CorsRule] }
//
//  forAll(verifyFormat(_: CorsRule))
//}
//
//it must "serialize lifecycle rule" in {
//  import ScalaCheckPropertyChecks._
//  import ScalacheckShapeless._
//
//  Inspectors.forAll {
//    Resource.my.getAsStream("lifecycle-rule_0.json").autoClosed(Json.parse) ::
//      Resource.my.getAsStream("lifecycle-rule_1.json").autoClosed(Json.parse) ::
//      Resource.my.getAsStream("lifecycle-rule_2.json").autoClosed(Json.parse) ::
//      Resource.my.getAsStream("lifecycle-rule_3.json").autoClosed(Json.parse) ::
//      Nil
//  } { verifyFormat[LifecycleRule] }
//
//  forAll(verifyFormat(_: LifecycleRule))
//}
//
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

}

object PlayJsonFormatsSpec {

  import VerifyJsonEncoding._

  class FormatEncoding[A: Format] extends Encoding[A] {
    override def read = Json.parse(_).as[A]
    override def write = (Json.toJson(_: A)) andThen Json.stringify _
  }

  object FormatEncoding {
    def apply[A: Format]: Encoding[A] = new FormatEncoding[A]
  }

//  case class Formatted[A](x: A, y: JsValue) {
//    def withShape(f: (A, JsValue) => Assertion)(implicit pos: Position): Assertion = f(x, y)
//    def withResult[B: Reads](f: (A, JsResult[B]) => Assertion)(implicit pos: Position): Assertion = f(x, y.validate[B])
//  }
//
//  def verifyFormat[A: Format](x: A)(implicit pos: Position) = {
//    import Json._
//    inside(toJson(x)) {
//      case y =>
//        y.as[A] should equal(x)
//        Formatted(x, toJson(x))
//    }
//  }
//
//  def verifyFormat[A: Format](y: JsValue)(implicit pos: Position) = {
//    import Json._
//    inside(y.validate[A]) {
//      case JsSuccess(x, _) =>
//        (toJson(x) should equal(y))(after being nullsRemoved)
//        Formatted(x, y)
//    }
//
//  }

}
