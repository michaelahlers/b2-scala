package ahlers.b2.api.v2

import better.files._
import org.scalacheck._
import org.scalactic._
import org.scalactic.source._
import org.scalatest.wordspec._
import org.scalatest.Inside._
import org.scalatest._
import org.scalatest.Matchers._
import org.scalatest.enablers.Containing
import org.scalatestplus.scalacheck._
import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
class PlayJsonFormatsSpec extends AnyWordSpec {

  import PlayJsonFormatsSpec._

  "Formats" can {

    import Json._
    import PlayJsonFormats._

    "serialize account authorization" in {
      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      Inspectors.forAll {
        Resource.my.getAsStream("authorize-account-response_0.json").autoClosed(Json.parse) ::
          Nil
      } { verifyFormat[AccountAuthorization] }

      forAll(verifyFormat(_: AccountAuthorization))
    }

    "serialize capability" in {
      import Capability._
      Inspectors.forAll(Capability.values) {
        verifyFormat(_) withResult [String] {
          case (ListKeys, y)      => y should contain("listKeys")
          case (WriteKeys, y)     => y should contain("writeKeys")
          case (DeleteKeys, y)    => y should contain("deleteKeys")
          case (ListBuckets, y)   => y should contain("listBuckets")
          case (WriteBuckets, y)  => y should contain("writeBuckets")
          case (DeleteBuckets, y) => y should contain("deleteBuckets")
          case (ListFiles, y)     => y should contain("listFiles")
          case (ReadFiles, y)     => y should contain("readFiles")
          case (ShareFiles, y)    => y should contain("shareFiles")
          case (WriteFiles, y)    => y should contain("writeFiles")
          case (DeleteFiles, y)   => y should contain("deleteFiles")
        }
      }
    }

    "serialize CORS rule" in {
      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      Inspectors.forAll {
        Resource.my.getAsStream("cors-rules_0.json").autoClosed(Json.parse(_).as[Seq[JsValue]])
      } { verifyFormat[CorsRule] }

      forAll(verifyFormat(_: CorsRule))
    }

    "serialize lifecycle rule" in {
      import ScalaCheckPropertyChecks._
      import ScalacheckShapeless._

      Inspectors.forAll {
        Resource.my.getAsStream("lifecycle-rule_0.json").autoClosed(Json.parse) ::
          Resource.my.getAsStream("lifecycle-rule_1.json").autoClosed(Json.parse) ::
          Resource.my.getAsStream("lifecycle-rule_2.json").autoClosed(Json.parse) ::
          Resource.my.getAsStream("lifecycle-rule_3.json").autoClosed(Json.parse) ::
          Nil
      } { verifyFormat[LifecycleRule] }

      forAll(verifyFormat(_: LifecycleRule))
    }

    "serialize operation" in {
      import Operation._
      Inspectors.forAll(Operation.values) {
        verifyFormat(_) withResult [String] {
          case (DownloadFileByName, y) => y should contain("b2_download_file_by_name")
          case (DownloadFileById, y)   => y should contain("b2_download_file_by_id")
          case (UploadFile, y)         => y should contain("b2_upload_file")
          case (UploadPart, y)         => y should contain("b2_upload_part")
        }
      }
    }

  }

}

object PlayJsonFormatsSpec {

  import Gen._

  implicit object ContainingJsResult extends Containing[JsResult[_]] {
    val delegate: Containing[Option[_]] = implicitly
    override def contains(container: JsResult[_], element: Any) = delegate.contains(container.asOpt, element)
    override def containsOneOf(container: JsResult[_], elements: collection.Seq[Any]) = delegate.containsOneOf(container.asOpt, elements)
    override def containsNoneOf(container: JsResult[_], elements: collection.Seq[Any]) = delegate.containsNoneOf(container.asOpt, elements)
  }

  val nullsRemoved: Uniformity[JsValue] = new Uniformity[JsValue] {

    override def normalizedCanHandle(x: Any) = x.isInstanceOf[JsValue]

    override def normalizedOrSame(x: Any) =
      x match {
        case y: JsValue => normalized(y)
        case _          => x
      }

    override def normalized(x: JsValue) = x match {
      case JsObject(xs) =>
        JsObject {
          xs flatMap {
            case (_, JsNull) => None
            case (k, v)      => Some((k, normalized(v)))
          }
        }
      case x => x
    }

  }

  case class Formatted[A](x: A, y: JsValue) {
    def withShape(f: (A, JsValue) => Assertion)(implicit pos: Position): Assertion = f(x, y)
    def withResult[B: Reads](f: (A, JsResult[B]) => Assertion)(implicit pos: Position): Assertion = f(x, y.validate[B])
  }

  def verifyFormat[A: Format](x: A)(implicit pos: Position) = {
    import Json._
    inside(toJson(x)) {
      case y =>
        y.as[A] should equal(x)
        Formatted(x, toJson(x))
    }
  }

  def verifyFormat[A: Format](y: JsValue)(implicit pos: Position) = {
    import Json._
    inside(y.validate[A]) {
      case JsSuccess(x, _) =>
        (toJson(x) should equal(y))(after being nullsRemoved)
        Formatted(x, y)
    }

  }

}
