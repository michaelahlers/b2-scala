package ahlers.b2.api.v2

/**
 * @author <a href="mailto:michael@ahlers.consulting">Michael Ahlers</a>
 */
case class LifecycleRule(
  daysFromHidingToDeleting: Option[DaysFromHidingToDeleting],
  daysFromUploadingToHiding: Option[DaysFromUploadingToDeleting],
  fileNamePrefix: FileNamePrefix)
