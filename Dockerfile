# Etapa de construcción (Build Stage)
FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copiar solo el pom.xml primero para cachear las dependencias
COPY pom.xml .
# Descargar dependencias (esto fallará si no hay internet, pero cacheará las libs)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar empaquetando, saltando tests
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# Etapa de ejecución (Run Stage)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Crear un usuario no root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
