package ahlers.b2.api.v2

import enumeratum.values._
import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object PlayJsonFormats extends PlayJsonFormats

trait PlayJsonFormats {

  implicit val ReadsAccountAuthorization: Reads[AccountAuthorization] = {
    import AccountAuthorization._
    implicit val ReadsAllowed: Reads[Allowed] = Json.reads
    Json.reads
  }

  implicit val WritesAccountAuthorization: OWrites[AccountAuthorization] = {
    import AccountAuthorization._
    implicit val WritesAllowed: OWrites[Allowed] = Json.writes[Allowed]
    Json.writes[AccountAuthorization]
  }

  implicit lazy val FormatBucketType: Format[BucketType] = EnumFormats.formats(BucketType)

  implicit lazy val FormatCapability: Format[Capability] = EnumFormats.formats(Capability)

  implicit val ReadsCorsRule: Reads[CorsRule] = Json.reads

  implicit val WritesCorsRule: OWrites[CorsRule] = Json.writes[CorsRule]

  implicit val ReadsLifecycleRule: Reads[LifecycleRule] = Json.reads

  implicit val WritesLifecycleRule: OWrites[LifecycleRule] = Json.writes[LifecycleRule]

  implicit lazy val FormatOperation: Format[Operation] = EnumFormats.formats(Operation)

}
