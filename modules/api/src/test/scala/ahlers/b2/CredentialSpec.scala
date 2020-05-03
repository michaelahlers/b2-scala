package ahlers.b2

import cats.syntax.option._
import org.scalacheck._
import org.scalamock.scalatest._
import org.scalatest._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec._
import org.scalatestplus.scalacheck._
import OptionValues._
import com.softwaremill.diffx.scalatest.DiffMatcher._

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

      forAll { (heads: Int, credential: Credential, tails: Int) =>
        val providers = Seq.fill(heads)(mock[Provider])

        providers
          .take(heads)
          .foreach(p =>
            (p.find _)
              .expects()
              .once()
              .returns(none))

        providers
          .drop(heads)
          .take(1)
          .foreach(p =>
            (p.find _)
              .expects()
              .once()
              .returns(none))

        providers
          .drop(heads)
          .drop(1)
          .foreach(p =>
            (p.find _)
              .expects()
              .never())

        providers
          .reduce(_ | _)
          .find()
          .value
          .should(matchTo(credential))
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
