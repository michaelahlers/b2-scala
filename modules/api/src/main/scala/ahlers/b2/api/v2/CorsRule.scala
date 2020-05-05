package ahlers.b2.api.v2

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class CorsRule(
  corsRuleName: CorsRuleName,
  allowedOrigins: Seq[AllowedOrigin],
  allowedOperations: Seq[Operation],
  allowedHeaders: Option[Seq[AllowedHeader]],
  exposeHeaders: Option[Seq[ExposeHeader]],
  maxAgeSeconds: MaxAgeSeconds)
