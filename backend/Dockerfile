# Build Layer using stabilized Java 25 environment
FROM maven:3.9.14-eclipse-temurin-25 AS build
WORKDIR /app

# Cache dependencies to speed up future builds on Render
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and compile application
COPY src ./src
RUN mvn clean package -DskipTests

# Execution Layer
FROM eclipse-temurin:25-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Run with production profile active
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
