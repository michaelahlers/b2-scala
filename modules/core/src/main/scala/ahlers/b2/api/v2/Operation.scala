package ahlers.b2.api.v2

import enumeratum.values._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
sealed abstract class Operation(val value: String) extends StringEnumEntry
object Operation extends StringEnum[Operation] {

  case object DownloadFileByName extends Operation("b2_download_file_by_name")
  case object DownloadFileById extends Operation("b2_download_file_by_id")
  case object UploadFile extends Operation("b2_upload_file")
  case object UploadPart extends Operation("b2_upload_part")

  override val values = findValues

}
