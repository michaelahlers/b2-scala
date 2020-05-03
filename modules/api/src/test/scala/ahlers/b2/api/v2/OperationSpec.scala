package ahlers.b2.api.v2

import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers.should.Matchers._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class OperationSpec extends AnyFlatSpec {

  behavior.of("Operation")

  it must "specify protocol literals" in {
    import Operation._
    Inspectors.forAll(Operation.values) { operation =>
      val value = operation.value
      operation match {
        case DownloadFileByName => value should equal("b2_download_file_by_name")
        case DownloadFileById => value should equal("b2_download_file_by_id")
        case UploadFile => value should equal("b2_upload_file")
        case UploadPart => value should equal("b2_upload_part")
      }
    }
  }

}
