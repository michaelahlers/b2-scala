package ahlers.b2

import cats.syntax.option._
import org.scalacheck._
import org.scalamock.scalatest._
import org.scalatest.Matchers._
import org.scalatest.flatspec._
import org.scalatestplus.scalacheck._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class CredentialSpec extends AnyFlatSpec with MockFactory {

  behavior of "Providers"

  it must "get credentials directly" in {
    import Credential._
    import ScalaCheckPropertyChecks._
    import ScalacheckShapeless._

    forAll { credential: Credential =>
      Provider
        .direct(credential.key, credential.secret)
        .find() should contain(credential)
    }
  }

  it must "get credentials from environment" in {
    import Credential._
    import Provider._
    import ScalaCheckPropertyChecks._
    import ScalacheckShapeless._

    forAll { (provider: Environment, credential: Credential) =>
      whenever(2 == Set(provider.key, provider.secret).size) {
        org.joor.Reflect
          .onClass("java.lang.ProcessEnvironment")
          .set(
            "theUnmodifiableEnvironment",
            new java.util.HashMap[String, String]() {
              putAll(System.getenv())
              put(provider.key, credential.key)
              put(provider.secret, credential.secret)
            }
          )

        Provider
          .environment(provider.key, provider.secret)
          .find() should contain(credential)
      }
    }
  }

  it must "get when chained" in {
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
