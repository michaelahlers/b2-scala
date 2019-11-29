lazy val `b2` = (project in file("."))
  .configs(IntegrationTest)
  .aggregate(
    `akka-http`,
    `api`,
    `play-json`
  )

lazy val `akka-http` = (project in file("modules") / "akka-http")
  .dependsOn(
    `api`,
    `api` % "test->test"
  )

lazy val `api` = (project in file("modules") / "api")
//.configs(IntegrationTest)
//.settings(Defaults.itSettings)

lazy val `play-json` = (project in file("modules") / "play-json")
  .dependsOn(
    `api`,
    `api` % "test->test"
  )
//.configs(IntegrationTest)
//.settings(Defaults.itSettings)

publish / skip := true
