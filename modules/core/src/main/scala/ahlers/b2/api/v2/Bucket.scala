package ahlers.b2.api.v2

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class Bucket(
    account: Account,
    id: String,
    name: String,
    `type`: BucketType,
    metadata: Map[String, String]
)
