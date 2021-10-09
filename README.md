# Game Of Three (go3)

Game Of Three Take Away Code Challenge by Mohsen Mesrzadeh
_**mohsenmesr@yahoo.com**_

This game has two different modes of running at all:

- Sync Mode: Based on REST calls, both players (apps) should be online and play with each other simultaneously

- Async Mode: Based on ActiveMQ broker, One player start the game and push its request to player2 topic and when other
  one get online, it will process the game and push again in requested topic in game-request hence everything handled
  asynchronously

You can find steps of how to run Sync-Service and Async-Service(via ActiveMQ) in two script.sh file beside the pom.xml
file

e.g. Before starting run
`mvn clean package
`then you can run your artifact in a java8+ docker image or by running various commands as below:

**Note:** Async players can start the game in the Sync-Mode if both players were up

**Sync-Player1:**

`java -jar ./target/go3-0.0.1-SNAPSHOT.jar --spring.profiles.active=syncP1
`

**Sync-Player2:**

`java -jar ./target/go3-0.0.1-SNAPSHOT.jar --spring.profiles.active=syncP2`

**ASync-Player1:**

`java -jar ./target/go3-0.0.1-SNAPSHOT.jar --spring.profiles.active=asyncP1
`

**Sync-Player2:**

`java -jar ./target/go3-0.0.1-SNAPSHOT.jar --spring.profiles.active=asyncP2`

**Note:** Before running Async apps make sure that you'd installed ActiveMQ and start its container on localhost:61616 (
default config) or change the application configuration YAML files