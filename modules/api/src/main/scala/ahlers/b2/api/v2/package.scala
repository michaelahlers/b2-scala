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

}
