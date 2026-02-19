# 🎯 OKR Management Backend

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.4-green?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue?style=for-the-badge&logo=postgresql)
![JWT](https://img.shields.io/badge/Security-JWT-red?style=for-the-badge)

Backend robusto y escalable para la gestión de **Objetivos y Resultados Clave (OKRs)**. Construido con **Spring Boot** y siguiendo las mejores prácticas de arquitectura limpia y principios SOLID.

---

## 📋 Tabla de Contenidos
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Ejecución](#-instalación-y-ejecución)
- [Configuración](#-configuración)
- [Documentación API](#-documentación-api)
- [Testing](#-testing)
- [Estructura del Proyecto](#-estructura-del-proyecto)

---

## ✨ Características

*   **Gestión de OKRs**: Creación separada de Objetivos y Resultados Clave (Key Results).
*   **Entidades Maestras**: Gestión de Periodos, Pilares, Iniciativas, Áreas y Owners.
*   **Seguridad Avanzada**: Autenticación mediante **JWT** (Access Token + Refresh Token).
*   **Control de Acceso**: Endpoints públicos para lectura (GET) y protegidos para escritura (POST/PUT/DELETE).
*   **Paginación**: Listados optimizados con paginación nativa de Spring Data JPA.
*   **Validaciones**: Validación de datos de entrada y manejo de excepciones global.
*   **CORS**: Configurado para permitir la integración con clientes frontend (ej. `http://127.0.0.1:5500`).

---

## 🛠 Tecnologías

*   **Lenguaje**: Java 17
*   **Framework**: Spring Boot 2.7.4
*   **Base de Datos**: PostgreSQL
*   **ORM**: Hibernate / Spring Data JPA
*   **Seguridad**: Spring Security + JJWT
*   **Documentación**: OpenAPI 3 (Swagger UI)
*   **Herramientas**: Lombok, Maven
*   **Testing**: JUnit 5, Mockito, MockMvc

---

## ⚙ Requisitos Previos

Asegúrate de tener instalado:
*   [Java JDK 17](https://www.oracle.com/java/technologies/downloads/)
*   [PostgreSQL](https://www.postgresql.org/download/)
*   [Maven](https://maven.apache.org/) (Opcional, el proyecto incluye `mvnw`)

---

## 🚀 Instalación y Ejecución

1.  **Clonar el repositorio**
    ```bash
    git clone https://github.com/tu-usuario/okr-backend.git
    cd okr-backend
    ```

2.  **Configurar la Base de Datos**
    Crea una base de datos en PostgreSQL llamada `okr_db`.

3.  **Configurar Variables de Entorno (Recomendado)**
    Para no exponer credenciales en el código, configura las siguientes variables en tu sistema o edita el archivo `src/main/resources/application-dev.properties` (solo para desarrollo local):

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/okr_db
    spring.datasource.username=tu_usuario_postgres
    spring.datasource.password=tu_password_postgres
    ```

4.  **Ejecutar la aplicación**
    ```bash
    ./mvnw spring-boot:run
    ```
    *El servidor iniciará en el puerto `8001` por defecto.*

---

## 🔧 Configuración

El proyecto utiliza perfiles de Spring. El perfil por defecto es `dev`.

### JWT Configuration
La aplicación genera claves seguras automáticamente al inicio, pero puedes configurar la expiración en `application.properties`:

```properties
# Expiración del Access Token (1 hora)
app.jwt-expiration-milliseconds=3600000
# Expiración del Refresh Token (7 días)
app.jwt-refresh-expiration-milliseconds=604800000
```

---

## 📖 Documentación API

Una vez iniciada la aplicación, puedes acceder a la documentación interactiva con **Swagger UI**:

👉 **[http://localhost:8001/swagger-ui.html](http://localhost:8001/swagger-ui.html)**

### Endpoints Principales

| Método | Endpoint | Descripción | Auth |
| :--- | :--- | :--- | :--- |
| `POST` | `/auth/login` | Iniciar sesión | ❌ |
| `GET` | `/objectives` | Listar objetivos (con KRs) | ❌ |
| `POST` | `/objectives` | Crear Objetivo | ✅ |
| `POST` | `/keyresults/{id}` | Crear Key Result para un Objetivo | ✅ |
| `GET` | `/periods` | Listar Periodos | ❌ |

*(✅ Requiere Token Bearer, ❌ Público)*

### Usuario por Defecto (Dev)
Al iniciar en modo desarrollo, se crea un usuario administrador automáticamente:
*   **Email**: `admin@example.com`
*   **Password**: `admin123`

---

## 🧪 Testing

El proyecto cuenta con tests unitarios para Servicios y Controladores.

Ejecutar todos los tests:
```bash
./mvnw test
```

---

## 📂 Estructura del Proyecto

```
src/main/java/com/example/okr_back
├── config/          # Configuraciones (OpenAPI, Security, CORS)
├── controller/      # Controladores REST
├── dto/             # Data Transfer Objects
├── entities/        # Entidades JPA
├── infra/           # Infraestructura (Exceptions, Security Filters)
├── mapper/          # Mappers de Entidad <-> DTO
├── persistence/     # Repositorios JPA
└── service/         # Lógica de Negocio
```

---

## 📄 Licencia

Este proyecto está bajo la Licencia [MIT](LICENSE).
