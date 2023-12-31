# Use an official OpenJDK runtime as a parent image
FROM adoptopenjdk:20-jre-hotspot
ADD target/acmeGateway-0.0.1-SNAPSHOT.jar acmeGateway.jar
ENTRYPOINT ["java","-jar","/acmeGateway.jar"]
