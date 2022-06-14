FROM openjdk:17-alpine
EXPOSE 12345
COPY build/libs/VaccineConverter-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=production", "/app.jar"]
