FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package spring-boot:repackage

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/Challenge-1.0-SNAPSHOT.jar ./Challenge-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "Challenge-1.0-SNAPSHOT.jar"]