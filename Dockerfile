# Use Maven + JDK image
FROM maven:3.9.1-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy all modules
COPY pom.xml .
COPY web web
COPY core core
COPY data data

# Build the project
RUN mvn clean package -DskipTests

# Use a lightweight JDK image for running
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/web/target/domuapi.web-1.0-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "app.jar"]
