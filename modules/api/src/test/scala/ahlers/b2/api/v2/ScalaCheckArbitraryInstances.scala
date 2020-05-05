package ahlers.b2.api.v2

import eu.timepit.refined.scalacheck.numeric._
import eu.timepit.refined.scalacheck.string._
import org.scalacheck._

object ScalaCheckArbitraryInstances {

  implicit val arbAccountId: Arbitrary[AccountId] = AccountId.deriving
  implicit val arbAuthorizationToken: Arbitrary[AuthorizationToken] = AuthorizationToken.deriving

  implicit val arbBucketId: Arbitrary[BucketId] = BucketId.deriving
  implicit val arbBucketName: Arbitrary[BucketName] = BucketName.deriving
  implicit val arbBucketNamePrefix: Arbitrary[BucketNamePrefix] = BucketNamePrefix.deriving

  implicit val arbPartSize: Arbitrary[PartSize] = PartSize.deriving

  implicit val arbApiUrl: Arbitrary[ApiUrl] = ApiUrl.deriving
  implicit val arbDownloadUrl: Arbitrary[DownloadUrl] = DownloadUrl.deriving

  implicit val arbCorsRuleName: Arbitrary[CorsRuleName] = CorsRuleName.deriving
  implicit val arbAllowedOrigin: Arbitrary[AllowedOrigin] = AllowedOrigin.deriving
  implicit val arbAllowedHeader: Arbitrary[AllowedHeader] = AllowedHeader.deriving
  implicit val arbExposeHeader: Arbitrary[ExposeHeader] = ExposeHeader.deriving
  implicit val arbMaxAgeSeconds: Arbitrary[MaxAgeSeconds] = MaxAgeSeconds.deriving

}
