package ahlers.b2.api.v2

import org.scalatest.matchers.should.Matchers._
import org.scalatest._
import org.scalatest.flatspec._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class BucketTypeSpec extends AnyFlatSpec {

  behavior of "Bucket Type"

  it must "specify protocol literals" in {
    import BucketType._
    Inspectors.forAll(BucketType.values) { x =>
      val y = x.value
      x match {
        case All        => y should equal("all")
        case AllPrivate => y should equal("allPrivate")
        case AllPublic  => y should equal("allPublic")
        case Snapshot   => y should equal("snapshot")
      }
    }
  }

}
