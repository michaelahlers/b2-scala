package ahlers.b2.core.api.v2

import ahlers.b2.core._
import io.lemonlabs.uri._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class AuthorizeAccountAllowedResponse(
    bucketId: String,
    bucketName: String,
    capabilities: Seq[Capability],
    namePrefix: Option[String]
)

object AuthorizeAccount

case class AuthorizeAccountResponse(
    absoluteMinimumPartSize: Long,
    accountId: String,
    allowed: AuthorizeAccountAllowedResponse,
    apiUrl: Url,
    authorizationToken: String,
    downloadUrl: Url,
    recommendedPartSize: Long
)
