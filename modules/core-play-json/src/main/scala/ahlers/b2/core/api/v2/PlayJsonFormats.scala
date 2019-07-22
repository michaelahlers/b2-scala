package ahlers.b2.core.api.v2

import ahlers.b2.core.{Authorization, Capability}
import io.lemonlabs.uri._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object PlayJsonFormats extends PlayJsonFormats
trait PlayJsonFormats {

  implicit val FormatAuthorizeAccountAllowedResponse: Format[AuthorizeAccountResponse] = Json.format
  implicit val FormatAuthorizeAccountResponse: Format[AuthorizeAccountResponse] = Json.format

  implicit val FormatAuthorization: Format[Authorization] = {
    import Authorization._

    implicit val FormatResponseAllowed: Format[AuthorizeAccountAllowedResponse] = Json.format
    implicit val FormatResponseAuthorization: Format[AuthorizeAccountResponse] = Json.format

  }

}
