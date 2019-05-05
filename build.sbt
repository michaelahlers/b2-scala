lazy val b2 = (project in file("."))
  .configs(IntegrationTest)
  .aggregate(core)

lazy val core = (project in file("modules") / "core")
  .configs(IntegrationTest)

publish / skip := true
