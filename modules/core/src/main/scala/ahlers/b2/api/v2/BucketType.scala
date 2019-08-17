package ahlers.b2.api.v2

import enumeratum._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait BucketType extends EnumEntry
object BucketType extends Enum[BucketType] {

  case object AllPrivate extends BucketType
  case object AllPublic extends BucketType
  case object Snapshot extends BucketType

  override val values = findValues

}
