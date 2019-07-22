package ahlers.b2.core.api.v2

import better.files._
import org.scalacheck._
import org.scalatest.wordspec._
import org.scalatest.Inside._
import org.scalatest.Matchers._
import org.scalatestplus.scalacheck._
import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class PlayJsonFormatsSpec extends AnyWordSpec {

  "Formats" can {

    import PlayJsonFormats._

    "serialize authorize account" in {
      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      inside(Resource.my.getAsStream("authorize-account-response.json").autoClosed(Json.parse)) {
        case response =>
          println(response)
          println(Json.toJson(response.as[Response]))
          Json.toJson(response.as[Response]) should equal(response)
      }

      forAll { x: Response =>
        inside(Json.toJson(x)) {
          case response =>
            response.as[Response] should equal(x)
        }
      }

    }

  }

}
