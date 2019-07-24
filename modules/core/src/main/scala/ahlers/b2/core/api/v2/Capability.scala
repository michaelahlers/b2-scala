package ahlers.b2.core.api.v2

import enumeratum._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait Capability extends EnumEntry
object Capability extends Enum[Capability] {

  case object ListKeys extends Capability
  case object WriteKeys extends Capability
  case object DeleteKeys extends Capability

  case object ListBuckets extends Capability
  case object WriteBuckets extends Capability
  case object DeleteBuckets extends Capability

  case object ListFiles extends Capability
  case object ReadFiles extends Capability
  case object ShareFiles extends Capability
  case object WriteFiles extends Capability
  case object DeleteFiles extends Capability

  override val values = findValues

}
