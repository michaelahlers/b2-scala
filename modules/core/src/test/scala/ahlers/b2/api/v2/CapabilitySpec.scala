package ahlers.b2.api.v2

import org.scalatest._
import org.scalatest.Matchers._
import org.scalatest.flatspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class CapabilitySpec extends AnyFlatSpec {

  behavior of "Capability"

  it must "specify protocol literals" in {
    import Capability._
    Inspectors.forAll(Capability.values) { x =>
      val y = x.value
      x match {
        case ListKeys      => y should equal("listKeys")
        case WriteKeys     => y should equal("writeKeys")
        case DeleteKeys    => y should equal("deleteKeys")
        case ListBuckets   => y should equal("listBuckets")
        case WriteBuckets  => y should equal("writeBuckets")
        case DeleteBuckets => y should equal("deleteBuckets")
        case ListFiles     => y should equal("listFiles")
        case ReadFiles     => y should equal("readFiles")
        case ShareFiles    => y should equal("shareFiles")
        case WriteFiles    => y should equal("writeFiles")
        case DeleteFiles   => y should equal("deleteFiles")
      }
    }
  }

}
