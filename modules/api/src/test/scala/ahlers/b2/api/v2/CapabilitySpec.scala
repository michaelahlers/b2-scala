package ahlers.b2.api.v2

import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers.should.Matchers._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class CapabilitySpec extends AnyFlatSpec {

  behavior of "Capability"

  it must "specify protocol literals" in {
    import Capability._
    Inspectors.forAll(Capability.values) { capability =>
      val value = capability.value
      capability match {
        case ListKeys      => value should equal("listKeys")
        case WriteKeys     => value should equal("writeKeys")
        case DeleteKeys    => value should equal("deleteKeys")
        case ListBuckets   => value should equal("listBuckets")
        case WriteBuckets  => value should equal("writeBuckets")
        case DeleteBuckets => value should equal("deleteBuckets")
        case ListFiles     => value should equal("listFiles")
        case ReadFiles     => value should equal("readFiles")
        case ShareFiles    => value should equal("shareFiles")
        case WriteFiles    => value should equal("writeFiles")
        case DeleteFiles   => value should equal("deleteFiles")
      }
    }
  }

}
