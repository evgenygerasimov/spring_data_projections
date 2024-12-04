FROM openjdk:18-jdk-alpine
WORKDIR /app
COPY target/spring_data_projections-1.0.0.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8080