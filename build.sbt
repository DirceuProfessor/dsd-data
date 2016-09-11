name := "dsd-data"

version := "0.1-SNAPSHOT"

libraryDependencies ++= Seq("org.mongodb" % "mongo-java-driver" % "2.10.1",
"org.apache.commons" % "commons-lang3" % "3.4"
, "junit" % "junit" % "4.12" % Test
,"com.novocode" % "junit-interface" % "0.11" % Test
        exclude("junit", "junit-dep")
)

mainClass in Compile := Some("br.unip.data.mongo.MongoConnector")
