ThisBuild / scalaVersion := "2.13.0"
ThisBuild / crossScalaVersions ++=
  "2.11.12" ::
    "2.12.8" ::
    Nil

/* TODO: Set Cats release version. */

ThisBuild / libraryDependencies ++=
  "org.typelevel" %% "cats-core" % "2.0.0-M4" ::
    "org.scalactic" %% "scalactic" % "3.0.8" ::
    Nil

ThisBuild / libraryDependencies ++=
  "org.typelevel" %% "cats-laws" % "2.0.0-M4" % Test ::
    "org.typelevel" %% "cats-testkit" % "2.0.0-M4" % Test ::
    "org.scalacheck" %% "scalacheck" % "1.14.0" % Test ::
    "org.scalamock" %% "scalamock" % "4.3.0" % Test ::
    "org.scalatest" %% "scalatest" % "3.0.8" % Test ::
    "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.3" % Test ::
    Nil

ThisBuild / libraryDependencies ++=
  "org.scalatest" %% "scalatest" % "3.0.8" % IntegrationTest ::
    Nil
