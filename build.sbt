lazy val root =
  project
    .aggregate(core)

lazy val core = project in (file("modules") / "core")

configs(IntegrationTest)
Defaults.itSettings

publish / skip := true
