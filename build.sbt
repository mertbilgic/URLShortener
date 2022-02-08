lazy val akkaVersion = "2.6.17"
lazy val slickVersion = "3.3.2"
lazy val akkaHttpVersion = "10.2.7"


lazy val commonSettings = Seq(
  scalaVersion := "2.13.7",

  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    "com.github.ancane" %% "hashids-scala" % "1.4"
  )
)

lazy val databaseDependencies = Seq(
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
)

lazy val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test
)

lazy val urlShortenerService = (project in file("urlShortenerService"))
  .settings(
    name := "URLShortener",
    commonSettings,
    libraryDependencies ++= akkaDependencies ++ databaseDependencies
  )