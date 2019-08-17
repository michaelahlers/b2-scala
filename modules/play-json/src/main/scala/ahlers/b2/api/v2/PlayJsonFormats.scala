package ahlers.b2.api.v2

import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object PlayJsonFormats extends PlayJsonFormats

trait PlayJsonFormats {

  implicit val ReadsAccountAuthorization: Reads[AccountAuthorization] = {
    import AccountAuthorization._
    import Capability._

    implicit val ReadsCapability: Reads[Capability] =
      implicitly[Reads[String]].reads(_) flatMap {
        case "listKeys"      => JsSuccess(ListKeys)
        case "writeKeys"     => JsSuccess(WriteKeys)
        case "deleteKeys"    => JsSuccess(DeleteKeys)
        case "listBuckets"   => JsSuccess(ListBuckets)
        case "writeBuckets"  => JsSuccess(WriteBuckets)
        case "deleteBuckets" => JsSuccess(DeleteBuckets)
        case "listFiles"     => JsSuccess(ListFiles)
        case "readFiles"     => JsSuccess(ReadFiles)
        case "shareFiles"    => JsSuccess(ShareFiles)
        case "writeFiles"    => JsSuccess(WriteFiles)
        case "deleteFiles"   => JsSuccess(DeleteFiles)
        case x               => JsError(JsonValidationError("error.authorization.capability.unknown", x))
      }

    implicit val ReadsAllowed: Reads[Allowed] = Json.reads

    Json.reads
  }

  implicit val WritesAccountAuthorization: OWrites[AccountAuthorization] = {
    import AccountAuthorization._
    import Capability._

    implicit val WritesCapability: Writes[Capability] =
      implicitly[Writes[String]] contramap {
        case ListKeys      => "listKeys"
        case WriteKeys     => "writeKeys"
        case DeleteKeys    => "deleteKeys"
        case ListBuckets   => "listBuckets"
        case WriteBuckets  => "writeBuckets"
        case DeleteBuckets => "deleteBuckets"
        case ListFiles     => "listFiles"
        case ReadFiles     => "readFiles"
        case ShareFiles    => "shareFiles"
        case WriteFiles    => "writeFiles"
        case DeleteFiles   => "deleteFiles"
      }

    implicit val WritesAllowed: OWrites[Allowed] = Json.writes[Allowed]

    Json.writes[AccountAuthorization]
  }

  implicit val ReadsLifecycleRule: Reads[LifecycleRule] = Json.reads

  implicit val WritesLifecycleRule: OWrites[LifecycleRule] = Json.writes[LifecycleRule]

}
