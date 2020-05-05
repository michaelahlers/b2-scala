package ahlers.b2.api

import eu.timepit.refined.api._
import eu.timepit.refined._
import io.estatico.newtype.macros.newtype

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 03, 2020
 */
package object v2 {

  type AccountIdRules = collection.NonEmpty
  type AccountIdType = String Refined AccountIdRules
  @newtype case class AccountId(toText: AccountIdType)

  type ApplicationKeyRules = collection.NonEmpty
  type ApplicationKeyType = String Refined ApplicationKeyRules
  @newtype case class ApplicationKey(toText: ApplicationKeyType)

  type ApplicationKeyIdRules = collection.NonEmpty
  type ApplicationKeyIdType = String Refined ApplicationKeyIdRules
  @newtype case class ApplicationKeyId(toText: ApplicationKeyIdType)

  type AuthorizationTokenRules = collection.NonEmpty
  type AuthorizationTokenType = String Refined AuthorizationTokenRules
  @newtype case class AuthorizationToken(toText: AuthorizationTokenType)

  type BucketIdRules = collection.NonEmpty
  type BucketIdType = String Refined BucketIdRules
  @newtype case class BucketId(toText: BucketIdType)

  type BucketNameRules = collection.NonEmpty
  type BucketNameType = String Refined BucketNameRules
  @newtype case class BucketName(toText: BucketNameType)

  type BucketNamePrefixRules = collection.NonEmpty
  type BucketNamePrefixType = String Refined BucketNamePrefixRules
  @newtype case class BucketNamePrefix(toText: BucketNamePrefixType)

  type PartSizeRules = numeric.NonNegative
  type PartSizeType = Int Refined PartSizeRules
  @newtype case class PartSize(toInt: PartSizeType)

  type ApiUrlRules = collection.NonEmpty // string.Url
  type ApiUrlType = String Refined ApiUrlRules
  @newtype case class ApiUrl(toText: ApiUrlType)

  type DownloadUrlRules = collection.NonEmpty // string.Url
  type DownloadUrlType = String Refined DownloadUrlRules
  @newtype case class DownloadUrl(toText: DownloadUrlType)

  type BucketRevisionRules = numeric.NonNegative
  type BucketRevisionType = Int Refined BucketRevisionRules
  @newtype case class BucketRevision(toInt: BucketRevisionType)

  type CorsRuleNameRules = collection.NonEmpty
  type CorsRuleNameType = String Refined CorsRuleNameRules
  @newtype case class CorsRuleName(toText: CorsRuleNameType)

  type AllowedOriginRules = collection.NonEmpty // string.Url
  type AllowedOriginType = String Refined AllowedOriginRules
  @newtype case class AllowedOrigin(toText: AllowedOriginType)

  type AllowedHeaderRules = collection.NonEmpty
  type AllowedHeaderType = String Refined AllowedHeaderRules
  @newtype case class AllowedHeader(toText: AllowedHeaderType)

  type ExposeHeaderRules = collection.NonEmpty
  type ExposeHeaderType = String Refined ExposeHeaderRules
  @newtype case class ExposeHeader(toText: ExposeHeaderType)

  type MaxAgeSecondsRules = numeric.NonNegative
  type MaxAgeSecondsType = Int Refined MaxAgeSecondsRules
  @newtype case class MaxAgeSeconds(toInt: MaxAgeSecondsType)

  type DaysFromHidingToDeletingRules = numeric.NonNegative
  type DaysFromHidingToDeletingType = Int Refined DaysFromHidingToDeletingRules
  @newtype case class DaysFromHidingToDeleting(toInt: DaysFromHidingToDeletingType)

  type DaysFromUploadingToDeletingRules = numeric.NonNegative
  type DaysFromUploadingToDeletingType = Int Refined DaysFromUploadingToDeletingRules
  @newtype case class DaysFromUploadingToDeleting(toInt: DaysFromUploadingToDeletingType)

  //type FileNamePrefixRules = ???
  type FileNamePrefixType = String //Refined FileNamePrefixRules
  @newtype case class FileNamePrefix(toText: FileNamePrefixType)

}
