package ahlers.b2.core.api.v2

import ahlers.b2.core.api.v2.Authorization._
import io.lemonlabs.uri._

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

  case class Allowed(
    bucket: Option[Bucket],
    capabilities: Seq[Capability],
    namePrefix:Option[String]
  )

  object Allowed {

    case class Bucket(
      id: String,
      name: String
    )

  }

  type Token = String

  case class PartSizes(
    minimum: Long,
    recommended: Long
  )

}
