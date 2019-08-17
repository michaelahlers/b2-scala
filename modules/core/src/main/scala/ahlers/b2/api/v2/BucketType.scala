package ahlers.b2.api.v2

import enumeratum.values._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed abstract class BucketType(val value: String) extends StringEnumEntry
object BucketType extends StringEnum[BucketType] {

  case object All extends BucketType("all")
  case object AllPrivate extends BucketType("allPrivate")
  case object AllPublic extends BucketType("allPublic")
  case object Snapshot extends BucketType("snapshot")

  override val values = findValues

}
