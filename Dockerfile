FROM openjdk:8-jre
EXPOSE 8080
ADD target/codechallenge-backend-back-0.0.1-SNAPSHOT.jar codechallenge-backend-back-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "codechallenge-backend-back-0.0.1-SNAPSHOT.jar"]
