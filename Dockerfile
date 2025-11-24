# ---- BUILD STAGE ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY . .
# Nếu dùng Maven Wrapper có trong project
RUN chmod +x mvnw
RUN ./mvnw -DskipTests clean package

# ---- RUN STAGE ----
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
