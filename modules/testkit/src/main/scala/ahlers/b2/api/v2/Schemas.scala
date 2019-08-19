package ahlers.b2.api.v2

//import better.files._
//import org.scalacheck._
import org.scalactic.source._
import org.scalatest.flatspec._
//import org.scalatest.Inside._
//import org.scalatest.Matchers._
//import org.scalatestplus.scalacheck._
//import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
trait Schemas[A] { this: AnyFlatSpecLike =>

  def parse(x: String): A

  def serializeAccountAuthorization(implicit pos: Position): Unit =
    it must "serialize account authorization" in {
//"samples" in {
//  import Inspectors._
//
//  forAll {
//    parse(Resource.my.getAsString("authorize-account-response_0.json")) ::
//      Nil
//  } { identity }
//}

//      "synthetic" in {
//import ScalaCheckPropertyChecks._
//import ScalacheckShapeless._
//
//forAll(verifyFormat(_: AccountAuthorization))
//      }

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

}
