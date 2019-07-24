package ahlers.b2.core.api.v2

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

    import PlayJsonFormats._

    "serialize authorization" in {
      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      inside(Resource.my.getAsStream("authorize-account-response.json").autoClosed(Json.parse)) {
        case response =>
          Json.toJson(response.as[Authorization]) should equal(response)
      }

      forAll { x: Authorization =>
        inside(Json.toJson(x)) {
          case response =>
            response.as[Authorization] should equal(x)
        }
      }

    }

  }

}

object PlayJsonFormatsSpec {

  implicit def arbUrl:Arbitrary[Url] = ???

  implicit val EqJsValue: Equality[JsValue] = {
    case (_:JsObject, _: JsObject) =>
      ???
    case (a, b) =>
      a === b
  }

}
