FROM openjdk:8
ADD target/docker-java.jar docker-java.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-java.jar"]