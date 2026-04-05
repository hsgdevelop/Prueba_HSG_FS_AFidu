# ============================================================
# Stage 1 — Build
# Usa la imagen oficial de Maven con Temurin 17 para compilar
# ============================================================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia el POM primero para aprovechar el cache de capas de Docker
# (solo re-descarga dependencias si cambia el pom.xml)
COPY pom.xml .
RUN mvn dependency:go-offline -B --no-transfer-progress

# Copia el código fuente y construye el JAR ejecutable
COPY src ./src
RUN mvn clean package -DskipTests -B --no-transfer-progress

# ============================================================
# Stage 2 — Runtime
# Imagen mínima con solo el JRE para ejecutar el JAR
# ============================================================
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia únicamente el JAR generado en el stage anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto en el que Spring Boot escucha (ver application.properties)
EXPOSE 8080

# Punto de entrada — arranca la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
