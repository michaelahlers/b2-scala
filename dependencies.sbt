ThisBuild / scalaVersion := "2.12.8"
ThisBuild / crossScalaVersions ++= "2.11.12" :: "2.13.0-M5" :: Nil

ThisBuild / libraryDependencies ++=
  "org.typelevel" %% "cats-core" % "1.6.0" ::
    "org.scalactic" %% "scalactic" % "3.0.7" ::
    Nil

ThisBuild / libraryDependencies ++=
  "org.typelevel" %% "cats-laws" % "1.6.0" % Test ::
    "org.typelevel" %% "cats-testkit" % "1.6.0" % Test ::
    "org.scalacheck" %% "scalacheck" % "1.14.0" % Test ::
    "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.0" % Test ::
    "org.scalatest" %% "scalatest" % "3.0.7" % Test ::
    Nil

ThisBuild / libraryDependencies ++=
  "org.scalatest" %% "scalatest" % "3.0.7" % IntegrationTest ::
    Nil
