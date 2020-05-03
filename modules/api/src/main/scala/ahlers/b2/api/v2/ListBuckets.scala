package ahlers.b2.api.v2

/**
 * @see [[https://backblaze.com/b2/docs/b2_list_buckets.html]]
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class ListBuckets(
  accountId: String,
  bucketId: Option[String],
  bucketName: Option[String],
  bucketTypes: Option[Seq[BucketType]])

case class BucketList(
  buckets: Seq[Bucket])
