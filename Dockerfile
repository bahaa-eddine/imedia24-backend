FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD /build/libs/imedia24api-0.0.1-SNAPSHOT.jar imedia24api.jar
ENTRYPOINT ["java", "-jar", "imedia24api.jar"]
