scalacOptions += "-Ypartial-unification"

/* TODO: Restore if the compiler settings plugin isn't updated by Scala 2.13. */
//scalacOptions --= {
//  CrossVersion.partialVersion(scalaVersion.value) match {
//    case Some((2, 13)) =>
//      "-Wdead-code" ::
//        Nil
//    case _ =>
//      Nil
//  }
//}
