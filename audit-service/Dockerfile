# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Compilar el proyecto y crear el JAR, saltando los tests para agilizar el build
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copiar el JAR generado en la etapa anterior
# Nota: El nombre del jar depende del <artifactId> y <version> en tu pom.xml
# Usamos un wildcard (*) para que funcione en cualquier microservicio
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]