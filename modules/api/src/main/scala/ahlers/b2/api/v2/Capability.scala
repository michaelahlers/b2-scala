package ahlers.b2.api.v2

import enumeratum.values._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed abstract class Capability(val value: String) extends StringEnumEntry
object Capability extends StringEnum[Capability] {

  case object ListKeys extends Capability("listKeys")
  case object WriteKeys extends Capability("writeKeys")
  case object DeleteKeys extends Capability("deleteKeys")

  case object ListBuckets extends Capability("listBuckets")
  case object WriteBuckets extends Capability("writeBuckets")
  case object DeleteBuckets extends Capability("deleteBuckets")

  case object ListFiles extends Capability("listFiles")
  case object ReadFiles extends Capability("readFiles")
  case object ShareFiles extends Capability("shareFiles")
  case object WriteFiles extends Capability("writeFiles")
  case object DeleteFiles extends Capability("deleteFiles")

  override val values = findValues

}
