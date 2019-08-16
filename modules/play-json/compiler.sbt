/**
 * Fix compilation where Play Json macro inception is used.
 */
scalacOptions --=
  "-Wunused:locals" ::
    "-Wunused:privates" ::
    "-Ywarn-unused:locals" ::
    "-Ywarn-unused:privates" ::
    Nil

/**
 * Need not be so strict with tests.
 */
Test / scalacOptions --=
  "-Wunused:imports" ::
    "-Ywarn-unused-import" ::
    "-Ywarn-unused:imports" ::
    Nil