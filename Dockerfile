FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ressy-home-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Specify the command to run on container start
CMD ["java", "-jar", "app.jar"]