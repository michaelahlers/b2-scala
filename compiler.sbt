ThisBuild / scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {

    ///**
    // * Enable experimental for single abstract method syntax.
    // * @see [[https://stackoverflow.com/a/22825239/700420]]
    // */
    //case Some((2, 11)) =>
    //  "-Xexperimental" ::
    //    "-Ypartial-unification" ::
    //    Nil

    case Some((2, 12)) =>
      "-Ypartial-unification" ::
        Nil

    case _ =>
      Nil

  }
}
