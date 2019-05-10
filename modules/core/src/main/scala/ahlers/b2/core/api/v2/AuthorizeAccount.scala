package ahlers.b2.core.api.v2

object AuthorizeAccount {

  case class Response(
      absoluteMinimumPartSize: Long,
      accountId: String,
      allowed: Response.Allowed,
      apiUrl: String,
      authorizationToken: String,
      downloadUrl: String,
      recommendedPartSize: Long
  )

  object Response {

    case class Allowed(
        bucketId: Option[String],
        bucketName: Option[String],
        capabilities: Seq[String],
        namePrefix: Option[String]
    )

  }

}
