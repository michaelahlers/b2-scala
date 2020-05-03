package ahlers.b2.api.v2

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class CorsRule(
  corsRuleName: String,
  allowedOrigins: Seq[String],
  allowedOperations: Seq[Operation],
  allowedHeaders: Option[Seq[String]],
  exposeHeaders: Option[Seq[String]],
  maxAgeSeconds: Int)
