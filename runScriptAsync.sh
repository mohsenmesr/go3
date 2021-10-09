#you need an activeMQ instance up and running on tcp://localhost:61616
mvn clean package
java -jar ./target/go3-0.0.1-SNAPSHOT.jar --spring.profiles.active=asyncP1
java -jar ./target/go3-0.0.1-SNAPSHOT.jar --spring.profiles.active=asyncP2