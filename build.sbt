name := "boids"
scalaVersion := "3.7.2"
scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings")
run / fork := true
Global / cancelable := true

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "cask" % "0.10.2",
  "org.scala-lang" %% "toolkit" % "0.7.0",
  "org.scala-lang" %% "toolkit-test" % "0.7.0" % Test,
)

