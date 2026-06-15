FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/anime-backend-*.jar app.jar

EXPOSE 8080

ENV DB_URL=jdbc:mysql://localhost:3306/anime_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
ENV DB_USERNAME=root
ENV DB_PASSWORD=root
ENV JWT_SECRET=my-super-secret-key-for-anime-backend-jwt-token-2026

ENTRYPOINT ["java", "-jar", "app.jar"]
