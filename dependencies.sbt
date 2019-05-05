ThisBuild / scalaVersion := "2.12.8"
ThisBuild / crossScalaVersions ++= "2.11.12" :: "2.13.0-M5" :: Nil

ThisBuild / libraryDependencies ++=
  "org.typelevel" %% "cats-core" % "1.6.+" ::
    "org.typelevel" %% "cats-laws" % "1.6.+" % Test ::
    "org.typelevel" %% "cats-testkit" % "1.6.+" % Test ::
    Nil
