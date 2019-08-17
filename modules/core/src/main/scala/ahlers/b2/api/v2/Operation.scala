package ahlers.b2.api.v2

import enumeratum._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed trait Operation extends EnumEntry
object Operation extends Enum[Operation] {

  case object DownloadFileByName extends Operation
  case object DownloadFileById extends Operation
  case object UploadFile extends Operation
  case object UploadPart extends Operation

  override val values = findValues

}
