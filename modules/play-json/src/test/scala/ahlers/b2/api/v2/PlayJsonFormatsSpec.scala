package ahlers.b2.api.v2

import better.files._
import org.scalacheck._
import org.scalactic._
import org.scalactic.source._
import org.scalatest.flatspec._
import org.scalatest.Inside._
import org.scalatest._
import org.scalatest.Matchers._
import org.scalatest.enablers.Containing
import org.scalatestplus.scalacheck._
import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class PlayJsonFormatsSpec extends AnyFlatSpec { //with Schemas[JsValue] {

  import PlayJsonFormatsSpec._
  import PlayJsonFormats._

  behavior of "Formats"

  //it must behave like serializeAccountAuthorization

  it must "serialize account authorization" in {
    import ScalaCheckPropertyChecks._
    import ScalacheckShapeless._

    Inspectors.forAll {
      Resource.my.getAsStream("authorize-account-response_0.json").autoClosed(Json.parse) ::
        Nil
    } { verifyFormat[AccountAuthorization] }

    forAll(verifyFormat(_: AccountAuthorization))
  }

  it must "serialize bucket type" in {
    Inspectors.forAll(BucketType.values) {
      verifyFormat(_)
    }
  }

  it must "serialize capability" in {
    Inspectors.forAll(Capability.values) {
      verifyFormat(_)
    }
  }

  it must "serialize CORS rule" in {
    import ScalaCheckPropertyChecks._
    import ScalacheckShapeless._

    Inspectors.forAll {
      Resource.my.getAsStream("cors-rules_0.json").autoClosed(Json.parse(_).as[Seq[JsValue]])
    } { verifyFormat[CorsRule] }

    forAll(verifyFormat(_: CorsRule))
  }

  it must "serialize lifecycle rule" in {
    import ScalaCheckPropertyChecks._
    import ScalacheckShapeless._

    Inspectors.forAll {
      Resource.my.getAsStream("lifecycle-rule_0.json").autoClosed(Json.parse) ::
        Resource.my.getAsStream("lifecycle-rule_1.json").autoClosed(Json.parse) ::
        Resource.my.getAsStream("lifecycle-rule_2.json").autoClosed(Json.parse) ::
        Resource.my.getAsStream("lifecycle-rule_3.json").autoClosed(Json.parse) ::
        Nil
    } { verifyFormat[LifecycleRule] }

    forAll(verifyFormat(_: LifecycleRule))
  }

  it must "serialize list buckets" in {
    import ScalaCheckPropertyChecks._
    import ScalacheckShapeless._

    Inspectors.forAll {
      Resource.my.getAsStream("list-buckets-request_0.json").autoClosed(Json.parse) ::
        Nil
    } { verifyFormat[ListBuckets] }

    forAll(verifyFormat(_: ListBuckets))
  }

  it must "serialize bucket list" in {
    import ScalaCheckPropertyChecks._
    import ScalacheckShapeless._

    Inspectors.forAll {
      Resource.my.getAsStream("list-buckets-response_0.json").autoClosed(Json.parse) ::
        Nil
    } { verifyFormat[BucketList] }

    forAll(verifyFormat(_: BucketList))
  }

  it must "serialize operation" in {
    Inspectors.forAll(Operation.values) {
      verifyFormat(_)
    }
  }

}

object PlayJsonFormatsSpec {

  import Gen._

  implicit object ContainingJsResult extends Containing[JsResult[_]] {
    val delegate: Containing[Option[_]] = implicitly
    override def contains(container: JsResult[_], element: Any) = delegate.contains(container.asOpt, element)
    override def containsOneOf(container: JsResult[_], elements: collection.Seq[Any]) = delegate.containsOneOf(container.asOpt, elements)
    override def containsNoneOf(container: JsResult[_], elements: collection.Seq[Any]) = delegate.containsNoneOf(container.asOpt, elements)
  }

  val nullsRemoved: Uniformity[JsValue] = new Uniformity[JsValue] {

    override def normalizedCanHandle(x: Any) = x.isInstanceOf[JsValue]

    override def normalizedOrSame(x: Any) =
      x match {
        case y: JsValue => normalized(y)
        case _          => x
      }

    override def normalized(x: JsValue) = x match {
      case JsObject(xs) =>
        JsObject {
          xs flatMap {
            case (_, JsNull) => None
            case (k, v)      => Some((k, normalized(v)))
          }
        }
      case x => x
    }

  }

  case class Formatted[A](x: A, y: JsValue) {
    def withShape(f: (A, JsValue) => Assertion)(implicit pos: Position): Assertion = f(x, y)
    def withResult[B: Reads](f: (A, JsResult[B]) => Assertion)(implicit pos: Position): Assertion = f(x, y.validate[B])
  }

  def verifyFormat[A: Format](x: A)(implicit pos: Position) = {
    import Json._
    inside(toJson(x)) {
      case y =>
        y.as[A] should equal(x)
        Formatted(x, toJson(x))
    }
  }

  def verifyFormat[A: Format](y: JsValue)(implicit pos: Position) = {
    import Json._
    inside(y.validate[A]) {
      case JsSuccess(x, _) =>
        (toJson(x) should equal(y))(after being nullsRemoved)
        Formatted(x, y)
    }

  }

}
