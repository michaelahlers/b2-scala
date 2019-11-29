package ahlers.b2.api.v2

import ahlers.b2.api.v2.AccountAuthorization._

/**
 * @see [[https://www.backblaze.com/b2/docs/b2_authorize_account.html]]
 */
case class AuthorizeAccount(
    applicationKeyId: String,
    applicationKey: String
)

case class AccountAuthorization(
    absoluteMinimumPartSize: Long,
    accountId: String,
    allowed: Allowed,
    apiUrl: String,
    authorizationToken: String,
    downloadUrl: String,
    recommendedPartSize: Long
)

object AccountAuthorization {

  case class Allowed(
      capabilities: Seq[Capability],
      bucketId: Option[String],
      bucketName: Option[String],
      namePrefix: Option[String]
  )

}
