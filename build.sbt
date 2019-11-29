lazy val `b2` = (project in file("."))
  .configs(IntegrationTest)
  .aggregate(
    `akka-http`,
    `api`,
    `play-json`,
    `testkit`
  )

lazy val `akka-http` = (project in file("modules") / "akka-http")
  .dependsOn(
    `api`,
    `testkit` % Test
  )

lazy val `api` = (project in file("modules") / "api")
//.configs(IntegrationTest)
//.settings(Defaults.itSettings)

lazy val `play-json` = (project in file("modules") / "play-json")
  .dependsOn(
    `api`,
    `testkit` % Test
  )
//.configs(IntegrationTest)
//.settings(Defaults.itSettings)

lazy val `testkit` = (project in file("modules") / "testkit")
  .dependsOn(`api`)

publish / skip := true
