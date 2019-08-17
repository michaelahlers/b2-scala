package ahlers.b2.api.v2

import better.files._
import io.lemonlabs.uri._
import org.scalacheck._
import org.scalactic._
import org.scalatest.wordspec._
import org.scalatest.Inside._
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

      inside(Resource.my.getAsStream("authorize-account-response.json").autoClosed(Json.parse)) {
        case response =>
          toJson(response.as[AccountAuthorization]) should equal(response)(after being nullsRemoved)
      }

      forAll { x: AccountAuthorization =>
        inside(toJson(x)) {
          case response =>
            response.as[AccountAuthorization] should equal(x)
        }
      }

    }

  }

}

object PlayJsonFormatsSpec {

  import Gen._

  implicit def arbUrl: Arbitrary[Url] = Arbitrary(identifier.map(Url.parse))

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
