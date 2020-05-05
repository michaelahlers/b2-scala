package ahlers.b2.api.v2

import enumeratum.values._
import eu.timepit.refined.api._
import play.api.libs.json._

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
object PlayJsonFormats extends PlayJsonFormats

trait PlayJsonFormats {

  implicit private def ReadsRefined[T, P, F[_, _]](implicit T: Reads[T], F: RefType[F], TP: Validate[T, P]): Reads[F[T, P]] =
    T.flatMap(F.refine(_).fold(Reads.failed(_), Reads.pure(_)))

  implicit private def WritesRefined[T, P, F[_, _]](implicit T: Writes[T], F: RefType[F]): Writes[F[T, P]] =
    T.contramap(F.unwrap(_))

  implicit val FormatAccountId: Format[AccountId] = AccountId.deriving

  implicit val FormatAuthorizationToken: Format[AuthorizationToken] = AuthorizationToken.deriving

  implicit val FormatBucketId: Format[BucketId] = BucketId.deriving
  implicit val FormatBucketName: Format[BucketName] = BucketName.deriving
  implicit val FormatBucketNamePrefix: Format[BucketNamePrefix] = BucketNamePrefix.deriving
  implicit val FormatBucketType: Format[BucketType] = EnumFormats.formats(BucketType)

  implicit val FormatCapability: Format[Capability] = EnumFormats.formats(Capability)

  implicit val FormatOperation: Format[Operation] = EnumFormats.formats(Operation)

  implicit val FormatPartSize: Format[PartSize] = PartSize.deriving

  implicit val FormatApiUrl: Format[ApiUrl] = ApiUrl.deriving

  implicit val FormatDownloadUrl: Format[DownloadUrl] = DownloadUrl.deriving

  implicit val FormatBucketRevision: Format[BucketRevision] = BucketRevision.deriving

  implicit val FormatCorsRuleName: Format[CorsRuleName] = CorsRuleName.deriving
  implicit val FormatAllowedOrigin: Format[AllowedOrigin] = AllowedOrigin.deriving
  implicit val FormatAllowedHeader: Format[AllowedHeader] = AllowedHeader.deriving
  implicit val FormatExposeHeader: Format[ExposeHeader] = ExposeHeader.deriving
  implicit val FormatMaxAgeSeconds: Format[MaxAgeSeconds] = MaxAgeSeconds.deriving

  implicit val FormatAccountAuthorization: Format[AccountAuthorization] = {
    import AccountAuthorization._
    implicit val FormatAllowed: Format[Allowed] = Json.format
    Json.format
  }

  implicit val FormatCorsRule: Format[CorsRule] = Json.format

  implicit val FormatLifecycleRule: Format[LifecycleRule] = Json.format

  implicit val FormatBucketList: Format[BucketList] = {
    implicit val FormatBucket: Format[Bucket] = Json.format
    Json.format
  }

  implicit val FormatListBuckets: Format[ListBuckets] = Json.format

}
