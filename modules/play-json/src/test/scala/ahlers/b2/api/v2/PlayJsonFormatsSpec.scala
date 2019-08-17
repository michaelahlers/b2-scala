package ahlers.b2.api.v2

import better.files._
import org.scalacheck._
import org.scalactic._
import org.scalatest.wordspec._
import org.scalatest.Inside._
import org.scalatest.Inspectors
import org.scalatest.Matchers._
import org.scalatestplus.scalacheck._
import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class PlayJsonFormatsSpec extends AnyWordSpec {

  import PlayJsonFormatsSpec._

  "Formats" can {

    import Json._
    import PlayJsonFormats._

    "serialize account authorization" in {
      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      Inspectors.forAll {
        Resource.my.getAsStream("authorize-account-response_0.json").autoClosed(Json.parse) ::
          Nil
      } { response =>
        toJson(response.as[AccountAuthorization]) should equal(response)(after being nullsRemoved)
      }

      forAll { x: AccountAuthorization =>
        inside(toJson(x)) {
          case response =>
            response.as[AccountAuthorization] should equal(x)
        }
      }

    }

    "serialize lifecycle rule" in {
      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      Inspectors.forAll {
        Resource.my.getAsStream("lifecycle-rule_0.json").autoClosed(Json.parse) ::
          Resource.my.getAsStream("lifecycle-rule_1.json").autoClosed(Json.parse) ::
          Resource.my.getAsStream("lifecycle-rule_2.json").autoClosed(Json.parse) ::
          Resource.my.getAsStream("lifecycle-rule_3.json").autoClosed(Json.parse) ::
          Nil
      } { response =>
        toJson(response.as[LifecycleRule]) should equal(response)(after being nullsRemoved)
      }

      forAll { x: LifecycleRule =>
        inside(toJson(x)) {
          case rule =>
            rule.as[LifecycleRule] should equal(x)
        }
      }

    }

  }

}

object PlayJsonFormatsSpec {

  import Gen._

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

}
