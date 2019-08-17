package ahlers.b2.api.v2

import enumeratum.values._
import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object PlayJsonFormats extends PlayJsonFormats

trait PlayJsonFormats {

  implicit val FormatAccountAuthorization: Format[AccountAuthorization] = {
    import AccountAuthorization._
    implicit val FormatAllowed: Format[Allowed] = Json.format
    Json.format
  }

  implicit lazy val FormatBucketType: Format[BucketType] = EnumFormats.formats(BucketType)

  implicit lazy val FormatCapability: Format[Capability] = EnumFormats.formats(Capability)

  implicit val FormatCorsRule: Format[CorsRule] = Json.format

  implicit val FormatLifecycleRule: Format[LifecycleRule] = Json.format

  implicit val FormatListBuckets: Format[ListBuckets] = Json.format

  implicit val FormatBucketList: Format[BucketList] = {
    implicit val FormatBucket: Format[Bucket] = Json.format
    Json.format
  }

  implicit lazy val FormatOperation: Format[Operation] = EnumFormats.formats(Operation)

}
