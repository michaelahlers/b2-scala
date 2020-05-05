package ahlers.b2.api.v2

/**
 * @see [[https://www.backblaze.com/b2/docs/b2_authorize_account.html]]
 */
case class AuthorizeAccount(
  applicationKeyId: ApplicationKeyId,
  applicationKey: ApplicationKey)
