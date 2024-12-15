# Stage 1: Build the application
FROM maven:3.8.1-openjdk-17 AS build

# Установим рабочую директорию
WORKDIR /app

# Скопируем файл pom.xml и загрузим зависимости
COPY pom.xml ./
RUN mvn dependency:go-offline

# Скопируем исходный код
COPY src ./src

# Соберем проект
RUN mvn clean package -DskipTests=true

# Stage 2: Run the application
FROM openjdk:20

# Установим рабочую директорию
WORKDIR /app

# Скопируем собранный jar файл из первого этапа
COPY --from=build /app/target/*.jar app.jar

# Откроем порт 8080
EXPOSE 8000

# Запустим приложение
ENTRYPOINT ["java", "-jar", "app.jar"]


