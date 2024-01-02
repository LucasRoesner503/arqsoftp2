# Use an official OpenJDK runtime as a parent image
FROM openjdk:21
ADD target/acmeGateway-0.0.1-SNAPSHOT.jar acmeGateway.jar
ENTRYPOINT ["java","-jar","/acmeGateway.jar"]
