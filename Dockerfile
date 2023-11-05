# Stage 1: Build the application
FROM maven:3.8-openjdk-18-slim AS build
WORKDIR /workspace/app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src src

# Package the application
RUN mvn package -DskipTests

# Stage 2: Create the Docker final image
FROM openjdk:18-slim

# Expose the port the app runs on
EXPOSE 8080

# Copy the built artifact from the build stage
COPY --from=build /workspace/app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java","-jar","/app.jar"]