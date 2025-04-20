# Use a lightweight OpenJDK image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy built jar to container
COPY target/swift_codes_api-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
