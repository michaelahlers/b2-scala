package ahlers.b2.api.v2

import org.scalatest.Matchers._
import org.scalatest._
import org.scalatest.flatspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class OperationSpec extends AnyFlatSpec {

  behavior of "Operation"

  it must "specify protocol literals" in {
    import Operation._
    Inspectors.forAll(Operation.values) { x =>
      val y = x.value
      x match {
        case DownloadFileByName => y should equal("b2_download_file_by_name")
        case DownloadFileById   => y should equal("b2_download_file_by_id")
        case UploadFile         => y should equal("b2_upload_file")
        case UploadPart         => y should equal("b2_upload_part")
      }
    }
  }

}
