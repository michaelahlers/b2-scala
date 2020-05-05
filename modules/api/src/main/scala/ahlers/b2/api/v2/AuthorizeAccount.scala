package ahlers.b2.api.v2

import ahlers.b2.api.v2.AccountAuthorization._

/**
 * @see [[https://www.backblaze.com/b2/docs/b2_authorize_account.html]]
 */
case class AuthorizeAccount(applicationKeyId: ApplicationKeyId, applicationKey: ApplicationKey)

case class AccountAuthorization(
  absoluteMinimumPartSize: PartSize,
  accountId: AccountId,
  allowed: Allowed,
  apiUrl: ApiUrl,
  authorizationToken: AuthorizationToken,
  downloadUrl: DownloadUrl,
  recommendedPartSize: PartSize)

object AccountAuthorization {

  case class Allowed(
    capabilities: Seq[Capability],
    bucketId: Option[BucketId],
    bucketName: Option[BucketName],
    namePrefix: Option[BucketNamePrefix])

}
