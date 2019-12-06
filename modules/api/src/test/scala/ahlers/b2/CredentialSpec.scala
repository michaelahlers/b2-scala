package ahlers.b2

import cats.syntax.option._
import org.scalacheck._
import org.scalamock.scalatest._
import org.scalatest._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._
import org.scalatestplus.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class CredentialSpec extends AnyWordSpec with MockFactory {

  "Providers" must {

    "get credentials directly" in {
      import Credential._
      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      forAll { credential: Credential =>
        Provider
          .direct(credential.key, credential.secret)
          .find() should contain(credential)
      }
    }

    "get credentials from environment" in {
      import Credential._
      import Provider._
      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      forAll { (key: String, secret: String, credential: Credential) =>
        /**
         * As of JDK 12, reflection can no longer be used to manipulate the system environment variables at runtime. Since then, a design concession is used to provide values
         * @see http://mail.openjdk.java.net/pipermail/core-libs-dev/2018-November/056486.html
         */
        val provider = new Environment(key, secret) {
          override val environment = Map((key, credential.key), (secret, credential.secret))
        }

        whenever(!(key == secret)) {
          provider.find() should contain(credential)
        }
      }
    }

    "get when chained" in {
      import Arbitrary._
      import Credential._
      import Gen._
      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      forAll(choose(0, 3), arbitrary[Credential], choose(0, 3)) { (heads, credential, tails) =>
        val providers: Seq[Provider] = {
          (Seq.fill(heads) {
            val p = mock[Provider]
            (p.find _).expects().once().returns(none)
            p
          } :+ {
            val p = mock[Provider]
            (p.find _).expects().once().returns(credential.some)
            p
          }) ++ Seq.fill(tails) {
            val p = mock[Provider]
            (p.find _).expects().never()
            p
          }
        }

        (providers reduce { _ | _ }).find() should contain(credential)
      }
    }
  }

  "Provider environment" must {
    "default to system" in {
      import Credential._
      import Provider._
      import Inside._

      inside(Provider.environment("", "")) {
        case provider: Environment =>
          provider.environment should be(sys.env)
      }

      inside(Environment("", "")) {
        case provider =>
          provider.environment should be(sys.env)
      }
    }
  }

}
