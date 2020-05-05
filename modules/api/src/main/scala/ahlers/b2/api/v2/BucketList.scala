package ahlers.b2.api.v2

/**
 * @see [[https://backblaze.com/b2/docs/b2_list_buckets.html]]
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class BucketList(buckets: Seq[Bucket])
