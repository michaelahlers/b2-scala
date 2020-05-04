libraryDependencies ++=
  "com.beachape" %% "enumeratum" % "1.5.13" ::
    "eu.timepit" %% "refined" % "0.9.14" ::
    "io.estatico" %% "newtype" % "0.4.3" ::
    "io.scalaland" %% "chimney" % "0.5.1" ::
    Nil

libraryDependencies ++=
  "com.typesafe.play" %% "play-json" % "2.7.4" % Test ::
    "eu.timepit" %% "refined-scalacheck" % "0.9.14" % Test ::
    "eu.timepit" %% "refined-shapeless" % "0.9.14" % Test ::
    Nil

libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 12)) =>
      compilerPlugin(("org.scalamacros" % "paradise" % "2.1.1").cross(CrossVersion.full)) ::
        Nil
    case _ =>
      Nil
  }
}
