# 🍽️ Sistema de Gestión de Restaurante - Arquitectura de Microservicios

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/JWT-Security-red)](https://jwt.io/)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-green)](https://swagger.io/)

Sistema modular de gestión de restaurante desarrollado con arquitectura de microservicios, implementando seguridad JWT, validación de datos y documentación automática con Swagger.

---

## 📋 Tabla de Contenidos

- [Características](#características)
- [Arquitectura](#arquitectura)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Ejecución](#ejecución)
- [Endpoints Principales](#endpoints-principales)
- [Testing](#testing)
- [Despliegue](#despliegue)
- [Documentación API](#documentación-api)
- [Contribuir](#contribuir)
- [Licencia](#licencia)

---

## ✨ Características

### Funcionalidades Principales

✅ **Autenticación y Autorización**
- Sistema de login con JWT
- Roles: ADMIN, MOZO, COCINERO, CAJERO
- Tokens con expiración de 24 horas
- Encriptación de contraseñas con BCrypt

✅ **Gestión de Clientes**
- CRUD completo de clientes
- Búsqueda por DNI
- Validación de datos
- Soft delete (desactivación lógica)

✅ **Gestión de Mesas**
- Control de estados (disponible, ocupada, reservada, mantenimiento)
- Asignación de mesas a clientes
- Control de capacidad
- Historial de asignaciones

✅ **Auditoría**
- Registro de todas las operaciones
- Trazabilidad por usuario
- Registro de IP y fecha/hora
- Consultas por entidad y acción

### Características Técnicas

🔐 **Seguridad**
- Autenticación stateless con JWT
- Autorización basada en roles
- Validación de tokens en cada petición
- CORS configurado

📊 **Base de Datos**
- MySQL 8.0
- JPA/Hibernate para ORM
- Migraciones automáticas
- Relaciones entre entidades

📝 **Validación**
- Bean Validation (JSR-380)
- Validaciones personalizadas
- Mensajes de error descriptivos

🐛 **Manejo de Errores**
- Manejo global de excepciones
- Respuestas HTTP estandarizadas
- Logs detallados

📚 **Documentación**
- Swagger UI en cada servicio
- OpenAPI 3.0
- Pruebas interactivas

---

## 🏗️ Arquitectura

### Microservicios

```
┌─────────────────────────────────────────────────────────────┐
│                         Cliente                              │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    API Gateway (Futuro)                      │
└─────────────────────────────────────────────────────────────┘
                            │
        ┌───────────────────┼───────────────────┐
        │                   │                   │
        ▼                   ▼                   ▼
┌──────────────┐   ┌──────────────┐   ┌──────────────┐
│ Auth Service │   │Cliente Service│   │ Mesa Service │
│   :8081      │   │    :8082      │   │    :8083     │
└──────────────┘   └──────────────┘   └──────────────┘
        │                   │                   │
        └───────────────────┼───────────────────┘
                            │
                            ▼
                  ┌──────────────────┐
                  │ Audit Service    │
                  │      :8084       │
                  └──────────────────┘
                            │
                            ▼
                  ┌──────────────────┐
                  │    MySQL DB      │
                  │  sabor_gourmet   │
                  └──────────────────┘
```

### Descripción de Servicios

| Servicio | Puerto | Responsabilidad |
|----------|--------|-----------------|
| **auth-service** | 8081 | Autenticación, autorización, gestión de usuarios |
| **cliente-service** | 8082 | CRUD de clientes, búsquedas, validaciones |
| **mesa-service** | 8083 | Gestión de mesas, asignaciones, estados |
| **audit-service** | 8084 | Registro de eventos, auditoría, trazabilidad |

---

## 🛠️ Tecnologías

### Backend

- **Java 17** - Lenguaje de programación
- **Spring Boot 3.2.0** - Framework principal
- **Spring Security** - Seguridad y autenticación
- **Spring Data JPA** - Persistencia de datos
- **Hibernate** - ORM
- **MySQL 8.0** - Base de datos relacional

### Seguridad

- **JWT (jjwt 0.12.3)** - Autenticación stateless
- **BCrypt** - Hash de contraseñas

### Documentación

- **Swagger/OpenAPI 3.0** - Documentación de API
- **SpringDoc** - Generación automática

### Utilidades

- **Lombok** - Reducción de código boilerplate
- **Bean Validation** - Validación de datos
- **SLF4J/Logback** - Sistema de logs

### Herramientas de Desarrollo

- **Maven** - Gestión de dependencias
- **Docker** - Contenedorización
- **Git** - Control de versiones

---

## 📦 Requisitos Previos

- **Java Development Kit (JDK) 17+**
  ```bash
  java -version
  ```

- **Maven 3.6+**
  ```bash
  mvn -version
  ```

- **MySQL 8.0+**
  ```bash
  mysql --version
  ```

- **Docker & Docker Compose** (opcional)
  ```bash
  docker --version
  docker-compose --version
  ```

- **Git**
  ```bash
  git --version
  ```

---

## 🚀 Instalación

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/saborgourmet-microservices.git
cd saborgourmet-microservices
```

### 2. Crear la Base de Datos

```sql
CREATE DATABASE sabor_gourmet_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Crear usuario (opcional)
CREATE USER 'saborgourmet'@'localhost' IDENTIFIED BY 'saborgourmet123';
GRANT ALL PRIVILEGES ON sabor_gourmet_db.* TO 'saborgourmet'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar Variables de Entorno

Crear archivo `.env` en la raíz del proyecto:

```bash
DB_HOST=localhost
DB_PORT=3306
DB_NAME=sabor_gourmet_db
DB_USER=root
DB_PASSWORD=tu_password
JWT_SECRET=MiClaveSecretaSuperSeguraParaJWT2024SaborGourmet123456789
```

---

## ⚙️ Configuración

### Opción 1: Configuración Manual

Editar `application.properties` en cada servicio:

```Properties
spring.datasource.url=jdbc:mysql://localhost:3306/sabor_gourmet_db
  spring.datasource.username=root
  spring.datasource.password=tu_password

  jwt.secret=MiClaveSecretaSuperSeguraParaJWT2024SaborGourmet123456789
```

### Opción 2: Variables de Entorno

Los servicios aceptan variables de entorno:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/sabor_gourmet_db
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=tu_password
export JWT_SECRET=tu_secret_seguro
```

---

## ▶️ Ejecución

### Opción 1: Ejecución Manual (Desarrollo)

Abrir 4 terminales y ejecutar cada servicio:

```bash
# Terminal 1 - Auth Service
cd auth-service
mvn spring-boot:run

# Terminal 2 - Cliente Service
cd cliente-service
mvn spring-boot:run

# Terminal 3 - Mesa Service
cd mesa-service
mvn spring-boot:run

# Terminal 4 - Audit Service
cd audit-service
mvn spring-boot:run
```

### Opción 2: Docker Compose (Recomendado)

```bash
# Construir y levantar todos los servicios
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down
```

### Verificar que los Servicios Estén Corriendo

- **Auth Service:** http://localhost:8081/swagger-ui.html
- **Cliente Service:** http://localhost:8082/swagger-ui.html
- **Mesa Service:** http://localhost:8083/swagger-ui.html
- **Audit Service:** http://localhost:8084/swagger-ui.html

---

## 🔗 Endpoints Principales

### Auth Service (8081)

| Método | Endpoint | Descripción | Público |
|--------|----------|-------------|---------|
| POST | `/api/auth/register` | Registrar usuario | ✅ |
| POST | `/api/auth/login` | Iniciar sesión | ✅ |
| GET | `/api/auth/validate` | Validar token | ✅ |
| GET | `/api/usuarios` | Listar usuarios | 🔒 ADMIN |

### Cliente Service (8082)

| Método | Endpoint | Descripción | Rol |
|--------|----------|-------------|-----|
| GET | `/api/clientes` | Listar clientes | 🔒 Autenticado |
| POST | `/api/clientes` | Crear cliente | 🔒 ADMIN/MOZO |
| GET | `/api/clientes/{id}` | Obtener por ID | 🔒 Autenticado |
| GET | `/api/clientes/dni/{dni}` | Buscar por DNI | 🔒 Autenticado |
| PUT | `/api/clientes/{id}` | Actualizar | 🔒 ADMIN/MOZO |
| DELETE | `/api/clientes/{id}` | Desactivar | 🔒 ADMIN |

### Mesa Service (8083)

| Método | Endpoint | Descripción | Rol |
|--------|----------|-------------|-----|
| GET | `/api/mesas` | Listar mesas | 🔒 Autenticado |
| POST | `/api/mesas` | Crear mesa | 🔒 ADMIN |
| GET | `/api/mesas/disponibles` | Mesas disponibles | 🔒 Autenticado |
| PATCH | `/api/mesas/{id}/estado` | Cambiar estado | 🔒 ADMIN/MOZO |
| POST | `/api/asignaciones` | Asignar mesa | 🔒 ADMIN/MOZO |
| PATCH | `/api/asignaciones/{id}/finalizar` | Finalizar asignación | 🔒 ADMIN/MOZO |

### Audit Service (8084)

| Método | Endpoint | Descripción | Rol |
|--------|----------|-------------|-----|
| GET | `/api/bitacora` | Listar eventos | 🔒 ADMIN |
| POST | `/api/bitacora` | Registrar evento | 🔒 Sistema |
| GET | `/api/bitacora/usuario/{id}` | Por usuario | 🔒 ADMIN |

---

## 🧪 Testing

### Con cURL

```bash
# 1. Registrar usuario
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123",
    "nombreCompleto": "Admin Sistema",
    "correo": "admin@test.com",
    "rol": "ADMIN"
  }'

# 2. Login y obtener token
TOKEN=$(curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }' | jq -r '.token')

# 3. Crear cliente
curl -X POST http://localhost:8082/api/clientes \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "dni": "12345678",
    "nombres": "Juan",
    "apellidos": "Pérez",
    "telefono": "987654321",
    "correo": "juan@test.com"
  }'

# 4. Listar clientes
curl -X GET http://localhost:8082/api/clientes \
  -H "Authorization: Bearer $TOKEN"
```

### Con Postman

1. Importar la colección: `postman_collection.json`
2. Configurar el entorno con las variables:
    - `auth_url`: http://localhost:8081
    - `cliente_url`: http://localhost:8082
    - `mesa_url`: http://localhost:8083
    - `audit_url`: http://localhost:8084
3. Ejecutar las peticiones en orden

---

## 🌐 Despliegue

### Railway

```bash
# Instalar Railway CLI
npm install -g @railway/cli

# Login
railway login

# Desplegar cada servicio
cd auth-service
railway init
railway up
```

### Render

1. Conectar repositorio de GitHub
2. Crear Web Service para cada microservicio
3. Configurar variables de entorno
4. Desplegar

### Heroku

```bash
# Login
heroku login

# Crear apps
heroku create saborgourmet-auth
heroku create saborgourmet-cliente
heroku create saborgourmet-mesa
heroku create saborgourmet-audit

# Desplegar
git subtree push --prefix auth-service heroku-auth master
```

---

## 📚 Documentación API

### Swagger UI

Acceder a la documentación interactiva:

- **Auth Service**: http://localhost:8081/swagger-ui.html
- **Cliente Service**: http://localhost:8082/swagger-ui.html
- **Mesa Service**: http://localhost:8083/swagger-ui.html
- **Audit Service**: http://localhost:8084/swagger-ui.html

### Autenticación en Swagger

1. Hacer login en `/api/auth/login`
2. Copiar el token JWT
3. Click en "Authorize" (candado verde)
4. Pegar el token (sin "Bearer ")
5. Probar endpoints protegidos

---

## 👥 Autor

- **Geraldine Solis** - [GitHub](https://github.com/GeraldineSolis)
