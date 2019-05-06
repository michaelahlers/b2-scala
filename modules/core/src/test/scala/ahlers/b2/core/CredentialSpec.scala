package ahlers.b2.core

import ahlers.b2.core.Credential.Provider._
import ahlers.b2.core.Credential._
import cats.syntax.option._
import org.scalacheck._
import org.scalamock.scalatest._
import org.scalatest.Matchers._
import org.scalatest._
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class CredentialSpec extends FlatSpec with MockFactory {

  behavior of "Providers"

  they can "get credentials directly" in {
    import ScalacheckShapeless._

    forAll { credential: Credential =>
      Provider
        .direct(credential.key, credential.secret)
        .get() should contain(credential)
    }
  }

  they can "get credentials from environment" in {
    import ScalacheckShapeless._

    forAll { (provider: Environment, credential: Credential) =>
      org.joor.Reflect
        .on("java.lang.ProcessEnvironment")
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
        .get() should contain(credential)
    }
  }

  they can "get when chained" in {
    import ScalacheckShapeless._

    forAll { (heads: Int, credential: Credential, tails: Int) =>
      val providers: Seq[Provider] = {
        (Seq.fill(heads) {
          val p = mock[Provider]
          (p.get _).expects().once().returns(none)
          p
        } :+ {
          val p = mock[Provider]
          (p.get _).expects().once().returns(credential.some)
          p
        }) ++ Seq.fill(tails) {
          val p = mock[Provider]
          (p.get _).expects().never()
          p
        }
      }

      (providers reduce { _ | _ }).get() should contain(credential)
    }
  }

}
