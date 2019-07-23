package ahlers.b2.core

import io.lemonlabs.uri._

import ahlers.b2.core.Authorization._

case class Authorization(
    account: Account,
    allowed: Allowed,
    token: Token,
    partSizes: PartSizes,
    service: Url,
    download: Url
)

object Authorization {

  case class Account(id: String)

  case class Allowed(
      bucket: Bucket,
      capabilities: Seq[Capability]
  )

  case class Bucket(
      id: Option[String],
      name: Option[String]
      capabilities: Set[Capability]
  )

  type Token = String

  case class PartSizes(
      minimum: Long,
      recommended: Long
  )

}
