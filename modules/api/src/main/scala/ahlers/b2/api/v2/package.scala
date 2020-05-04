package ahlers.b2.api

import eu.timepit.refined.api._
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean._
import eu.timepit.refined.collection._
import eu.timepit.refined.numeric._
import io.estatico.newtype.macros.newtype

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 * @since May 03, 2020
 */
package object v2 {

  type AccountIdRules = NonEmpty
  type AccountIdType = String Refined AccountIdRules
  @newtype case class AccountId(toText: AccountIdType)

  type ApplicationKeyRules = NonEmpty
  type ApplicationKeyType = String Refined ApplicationKeyRules
  @newtype case class ApplicationKey(toText: ApplicationKeyType)

  type ApplicationKeyIdRules = NonEmpty
  type ApplicationKeyIdType = String Refined ApplicationKeyIdRules
  @newtype case class ApplicationKeyId(toText: ApplicationKeyIdType)

  type AuthorizationTokenRules = NonEmpty
  type AuthorizationTokenType = String Refined AuthorizationTokenRules
  @newtype case class AuthorizationToken(toText: AuthorizationTokenType)

  type BucketIdRules = NonEmpty
  type BucketIdType = String Refined BucketIdRules
  @newtype case class BucketId(toText: BucketIdType)

  type BucketNameRules = NonEmpty
  type BucketNameType = String Refined BucketNameRules
  @newtype case class BucketName(toText: BucketNameType)

  type BucketNamePrefixRules = NonEmpty
  type BucketNamePrefixType = String Refined BucketNamePrefixRules
  @newtype case class BucketNamePrefix(toText: BucketNamePrefixType)

  type PartSizeRules = NonNegative
  type PartSizeType = Int Refined PartSizeRules
  @newtype case class PartSize(toInt: PartSizeType)

  type UrlRules = NonEmpty
  type UrlType = String Refined UrlRules
  @newtype case class Url(toText: UrlType)

}
