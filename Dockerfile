# Use the official JDK 21 slim image from the Docker Hub
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download dependencies (optional if you already built the jar)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project (this will include your target/ directory if it's already built)
COPY . .

# Build the application (make sure the jar is in the target directory)
RUN mvn clean package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "target/websocket-springBoot-0.0.1-SNAPSHOT.jar"]
