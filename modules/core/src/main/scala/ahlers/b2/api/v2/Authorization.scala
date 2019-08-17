package ahlers.b2.api.v2

import ahlers.b2.api.v2.Authorization.Allowed._
import ahlers.b2.api.v2.Authorization._
import io.lemonlabs.uri._

/**
 * @see [[https://www.backblaze.com/b2/docs/b2_authorize_account.html]]
 */
case class AuthorizeAccount(credential: Credential)

case class Authorization(
    account: Account,
    allowed: Allowed,
    token: Token,
    partSizes: PartSizes,
    service: Url,
    download: Url
)

object Authorization {

  /**
   * @param bucket Present if the bucket's id. is available.
   */
  case class Allowed(
      capabilities: Seq[Capability],
      bucket: Option[Bucket],
      namePrefix: Option[String]
  )

  object Allowed {

    /**
     * @param name Only present when the `id` is set.
     */
    case class Bucket(
        id: String,
        name: Option[String]
    )

  }

  type Token = String

  case class PartSizes(
      minimum: Long,
      recommended: Long
  )

}
