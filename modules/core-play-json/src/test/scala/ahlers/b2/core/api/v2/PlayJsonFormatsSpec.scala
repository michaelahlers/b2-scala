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
          val document = Json.toJson(response.as[Authorization])
          println(s"${response.getClass}; ${document.getClass}")
          foo should equal(response)
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

  import Gen._

  implicit def arbUrl:Arbitrary[Url] = Arbitrary(identifier.map(Url.parse))

  implicit lazy val EqJsValue: Equality[JsValue] = {
    case (x:JsObject, y: JsObject) =>
      x.keys forall { key =>
        (y \ key)
      }

      x.fieldSet === y.fieldSet
    case (x, y) =>
      x === y
  }

}
