FROM openjdk:21-jdk-slim

WORKDIR /app

COPY ./target/websocket-springBoot-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

# Command to run the application.
CMD ["java", "-jar", "websocket-springBoot-0.0.1-SNAPSHOT.jar"]
