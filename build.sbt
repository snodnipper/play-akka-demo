val AkkaVersion = "2.6.8"
val akkaManagementVersion =  "1.0.8"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, JavaAgent)
  .settings(
    name         := "play-java-websocket-example",
    version      := "1.0",
    scalaVersion := "2.13.3",
    // https://github.com/sbt/junit-interface
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-v"),
    libraryDependencies ++= Seq(
      guice,
      ws,
      "org.webjars" %% "webjars-play" % "2.8.0",
      "org.webjars" % "bootstrap" % "3.0.0",
      "org.webjars" % "flot"          % "0.8.3",
      // Maps
      "de.grundid.opendatalab" % "geojson-jackson"         % "1.14",
      "com.typesafe.akka"      % "akka-slf4j_2.13"         % AkkaVersion,
      "com.typesafe.akka"      % "akka-actor-typed_2.13"   % AkkaVersion,
      "com.typesafe.akka"      % "akka-actor_2.13"         % AkkaVersion,
      "com.typesafe.akka"      % "akka-cluster_2.13"       % AkkaVersion,
      "com.typesafe.akka"      % "akka-cluster-typed_2.13" % AkkaVersion,
      "com.typesafe.akka"      % "akka-cluster-tools_2.13" % AkkaVersion,
      // this should not be necessary _but_ see:  https://github.com/akka/akka/issues/29351
      "com.typesafe.akka"      % "akka-serialization-jackson_2.13" % AkkaVersion,
      "com.lightbend.akka.management" % "akka-management_2.13" % akkaManagementVersion,
      "com.lightbend.akka.management" % "akka-management-cluster-http_2.13" % akkaManagementVersion,
      "com.typesafe.akka"      % "akka-persistence_2.13" % AkkaVersion,
      "com.typesafe.akka"      % "akka-cluster-sharding_2.13" % AkkaVersion,
      "com.typesafe.akka"      % "akka-protobuf_2.13" % AkkaVersion,
      // bugs with 10.2.0: https://github.com/playframework/playframework/pull/10411
      "com.typesafe.akka"      % "akka-http_2.13" % "10.1.12",
      "com.typesafe.akka"      % "akka-http-spray-json_2.13" % "10.1.12",
      "com.typesafe.akka"      % "akka-stream_2.13" % AkkaVersion,
      // Testing libraries for dealing with CompletionStage...
      "org.assertj"    % "assertj-core" % "3.14.0" % Test,
      "org.awaitility" % "awaitility"   % "4.0.1"  % Test,
      "org.webjars" % "knockout" % "2.3.0",
      "org.webjars" % "requirejs" % "2.1.11-1",
      "org.webjars" % "leaflet" % "0.7.2",
      "org.webjars" % "rjs" % "2.1.11-1-trireme" % "test",
      "org.webjars" % "squirejs" % "0.1.0" % "test",
//      // Kamon
//      "io.kamon" %% "kamon-bundle" % "2.1.0",
//      "io.kamon" %% "kamon-apm-reporter" % "2.1.0"
    ),
    LessKeys.compress := true,
    javacOptions ++= Seq(
      "-Xlint:unchecked",
      "-Xlint:deprecation"
    )
  )
