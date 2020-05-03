package ahlers.b2.api.v2

import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers.should.Matchers._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class BucketTypeSpec extends AnyFlatSpec {

  behavior.of("Bucket Type")

  it must "specify protocol literals" in {
    import BucketType._
    Inspectors.forAll(BucketType.values) { bucketType =>
      val value = bucketType.value
      bucketType match {
        case All => value should equal("all")
        case AllPrivate => value should equal("allPrivate")
        case AllPublic => value should equal("allPublic")
        case Snapshot => value should equal("snapshot")
      }
    }
  }

}
