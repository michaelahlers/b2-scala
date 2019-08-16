package ahlers.b2.core.api.v2

import ahlers.b2.core.api.v2.Authorization._
import io.lemonlabs.uri._

/**
 *  @see [[https://backblaze.com/b2/docs/b2_authorize_account.html]]
 */
case class Authorization(
    account: Account,
    allowed: Allowed,
    token: Token,
    partSizes: PartSizes,
    service: Url,
    download: Url
)

object Authorization {

  import Allowed._

  case class Account(id: String)

  /**
   * @param bucket Present if the bucket's id. is available.
   */
  case class Allowed(
      bucket: Option[Bucket],
      capabilities: Seq[Capability],
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
