package ahlers.b2.api.v2

import ahlers.b2.api.v2.ListBuckets._

/**
 * @see [[https://backblaze.com/b2/docs/b2_list_buckets.html]]
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class ListBuckets(
    account: Account,
    bucket: BucketFilter
)

object ListBuckets {

  case class BucketFilter(
      id: Option[String],
      name: Option[String],
      types: Option[Seq[BucketType]]
  )

}

case class BucketList(buckets: Seq[Bucket])

object BucketList {}
