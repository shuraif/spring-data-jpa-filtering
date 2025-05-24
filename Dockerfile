# Build Stage
FROM maven:3.9.5-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Pass Jasypt password as an ARG
ARG JASYPT_PASSWORD

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built JAR
COPY --from=build /app/target/filter-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Set environment variable
ARG JASYPT_PASSWORD
ENV JASYPT_PASSWORD=${JASYPT_PASSWORD}

# Run the application with memory limits
CMD ["sh", "-c", "java -Xmx256m -Xms128m -Djasypt.encryptor.password=$JASYPT_PASSWORD -Dspring.profiles.active=prod -jar app.jar"]
