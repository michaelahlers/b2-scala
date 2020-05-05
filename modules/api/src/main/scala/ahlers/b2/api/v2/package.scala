package ahlers.b2.api

import eu.timepit.refined.api._
import eu.timepit.refined._
import io.estatico.newtype.macros.newtype

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 03, 2020
 */
package object v2 {

  type AccountIdType = String Refined collection.NonEmpty
  @newtype case class AccountId(toText: AccountIdType)

  type ApplicationKeyType = String Refined collection.NonEmpty
  @newtype case class ApplicationKey(toText: ApplicationKeyType)

  type ApplicationKeyIdType = String Refined collection.NonEmpty
  @newtype case class ApplicationKeyId(toText: ApplicationKeyIdType)

  type AuthorizationTokenType = String Refined collection.NonEmpty
  @newtype case class AuthorizationToken(toText: AuthorizationTokenType)

  type BucketIdType = String Refined collection.NonEmpty
  @newtype case class BucketId(toText: BucketIdType)

  type BucketNameType = String Refined collection.NonEmpty
  @newtype case class BucketName(toText: BucketNameType)

  type BucketNamePrefixType = String Refined collection.NonEmpty
  @newtype case class BucketNamePrefix(toText: BucketNamePrefixType)

  type PartSizeType = Int Refined numeric.NonNegative
  @newtype case class PartSize(toInt: PartSizeType)

  type ApiUrlType = String Refined collection.NonEmpty // string.Url
  @newtype case class ApiUrl(toText: ApiUrlType)

  type DownloadUrlType = String Refined collection.NonEmpty // string.Url
  @newtype case class DownloadUrl(toText: DownloadUrlType)

  type BucketRevisionType = Int Refined numeric.NonNegative
  @newtype case class BucketRevision(toInt: BucketRevisionType)

  type CorsRuleNameType = String Refined collection.NonEmpty
  @newtype case class CorsRuleName(toText: CorsRuleNameType)

  type AllowedOriginType = String Refined collection.NonEmpty // string.Url
  @newtype case class AllowedOrigin(toText: AllowedOriginType)

  type AllowedHeaderType = String Refined collection.NonEmpty
  @newtype case class AllowedHeader(toText: AllowedHeaderType)

  type ExposeHeaderType = String Refined collection.NonEmpty
  @newtype case class ExposeHeader(toText: ExposeHeaderType)

  type MaxAgeSecondsType = Int Refined numeric.NonNegative
  @newtype case class MaxAgeSeconds(toInt: MaxAgeSecondsType)

  type DaysFromHidingToDeletingType = Int Refined numeric.NonNegative
  @newtype case class DaysFromHidingToDeleting(toInt: DaysFromHidingToDeletingType)

  type DaysFromUploadingToDeletingType = Int Refined numeric.NonNegative
  @newtype case class DaysFromUploadingToDeleting(toInt: DaysFromUploadingToDeletingType)

  type FileNamePrefixType = String //Refined ???
  @newtype case class FileNamePrefix(toText: FileNamePrefixType)

}
