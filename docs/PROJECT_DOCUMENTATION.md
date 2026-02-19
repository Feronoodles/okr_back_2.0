# Documentación del Proyecto OKR Backend

## 1. Descripción General
Backend para una aplicación de gestión de OKRs (Objectives and Key Results).
- **Tecnología**: Spring Boot 2.7.4, Java 17
- **Base de Datos**: PostgreSQL
- **Seguridad**: JWT (JSON Web Tokens) con Spring Security
- **Documentación API**: OpenAPI / Swagger UI
- **Testing**: JUnit 5, Mockito, MockMvc

## 2. Arquitectura
El proyecto sigue una arquitectura en capas:
- **Controller**: Maneja las peticiones HTTP (`/controller`).
- **Service**: Contiene la lógica de negocio (`/service`).
- **Persistence**: Interfaces de repositorios JPA (`/persistence`).
- **Entities**: Modelos de datos (`/entities`).
- **DTO**: Objetos de transferencia de datos (`/dto`).
- **Infra**: Configuración transversal (Seguridad, Excepciones) (`/infra`).

## 3. Entidades Principales

### Maestros
- **Period**: Periodos de tiempo (ej. "Q1 2026"). *Nombre único*.
- **Pilar**: Pilares estratégicos. *Nombre único*.
- **Iniciativa**: Iniciativas globales. *Nombre único*.
- **Area**: Áreas de la empresa. *Nombre único*.
- **Owner**: Responsables de los KRs. *Nombre único*.

### Core OKR
- **Objective**: El objetivo cualitativo. Relacionado con Periodo, Pilar e Iniciativa. *Descripción única*.
- **KeyResult**: Resultados clave cuantitativos. Relacionados con Objetivo, Área y Owner. *Código NO único* (se permite repetir códigos como "KR1").

### Usuarios
- **User**: Usuarios del sistema con autenticación segura (BCrypt). ID tipo UUID.

## 4. Seguridad
- **Autenticación**: JWT (Access Token + Refresh Token).
- **Endpoints Públicos**:
    - `POST /auth/login`
    - `POST /auth/refresh`
    - `GET /objectives` (Incluye Key Results asociados)
    - `GET /periods`
    - `GET /pilares`
    - `GET /iniciativas`
    - `GET /areas`
    - `GET /owners`
- **CORS**: Configurado para permitir acceso desde `http://127.0.0.1:5500`.

## 5. Endpoints API

### Autenticación (`/auth`)
- `POST /login`: Iniciar sesión. Retorna JWT y Refresh Token.
- `POST /refresh`: Obtener nuevo Access Token usando Refresh Token.

### Objetivos (`/objectives`)
- `GET /`: Listar objetivos (paginado). Incluye Key Results. **(Público)**
- `POST /`: Crear un nuevo objetivo (sin Key Results). **(Requiere Auth)**

### Resultados Clave (`/keyresults`)
- `POST /{objectiveId}`: Crear un KR y asignarlo a un objetivo existente. **(Requiere Auth)**

### Maestros (Requieren Auth para crear, Públicos para leer)
- `/periods`: GET/POST
- `/pilares`: GET/POST
- `/iniciativas`: GET/POST
- `/areas`: GET/POST
- `/owners`: GET/POST

## 6. Configuración
- **Puerto**: 8001 (Dev)
- **Swagger UI**: `http://localhost:8001/swagger-ui.html`
- **Tests**: Ejecutar con `./mvnw test`
