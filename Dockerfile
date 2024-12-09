FROM maven:3.8.1-jdk-20 AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
COPY resources ./resources
COPY migration ./migration
RUN mvn clean package -DskipTests=true

FROM openjdk:20
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

