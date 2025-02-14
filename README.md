# 🐸 Frogger API: El Desafío del Récord 🎮🚀
 ¡Domina el ranking, sigue a tus rivales y alcanza la cima! 

#### 🔹Proyecto DAM - Teresa Charlo Millán🔹

## Índice

- [Introducción](#introducción)
- [Funcionalidades](#funcionalidades)
- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Guía de instalación](#guía-de-instalación)
- [Guía de uso](#guía-de-uso)
- [Conclusión](#conclusión)
- [Contribuciones, agradecimientos y referencias](#contribuciones-agradecimientos-y-referencias)
- [Licencia](#licencia)
- [Contacto](#contacto)

---

## Introducción

Frogger API es una aplicación desarrollada en **Spring Boot** como parte del ciclo formativo **DAM (Desarrollo de Aplicaciones Multiplataforma)**. Su objetivo es proporcionar un backend robusto para gestionar usuarios, puntuaciones récord y relaciones de seguimiento.

### Objetivos

- Implementar un backend en **Spring Boot** con estructura **MVC**.
- Manejar autenticación y autorización con **Spring Security y JWT**.
- Permitir a los usuarios gestionar su perfil, puntuaciones y seguidores.
- Desplegar la API en **Azure**.

---

## Funcionalidades

1. **Gestión de Usuarios**
   - Registro y autenticación con **JWT**.
   - Consultar perfil propio y perfiles públicos.
   - Buscar usuarios por nombre.
   - Eliminar usuarios.
2. **Sistema de puntuaciones**

   - Registrar puntuaciones récord.
   - Consultar y actualizar puntuaciones.
   - Ver clasificación global.

3. **Sistema de Seguidores**
   - Seguir y dejar de seguir a usuarios.
   - Ver lista de seguidores y seguidos.
   - Eliminar seguidores.

---

## Tecnologías Utilizadas

- **Backend**: Java 17, Spring Boot, Spring Security, Spring Data JPA
- **Base de Datos**: MySQL
- **Autenticación**: JWT
- **Despliegue**: Azure for Students
- **Herramientas**: Postman, IntelliJ IDEA, Git, GitHub

---

## Guía de instalación

### 1️⃣ Requisitos previos

- Tener **Java 17** instalado.
- Tener **MySQL** configurado.
- Instalar **Maven**.
- Tener un entorno de desarrollo como **IntelliJ IDEA** o **VS Code**.

### 2️⃣ Clonar el repositorio

```bash
 git clone https://github.com/teita98/FroggerAPI.git
 cd FroggerAPI
```

### 3️⃣ Configurar la base de datos

En `application.properties`, configura los accesos a la base de datos MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/frogger_db
spring.datasource.username=root
spring.datasource.password=tucontraseña
```

### 4️⃣ Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080/api/v1/`.

---

## Guía de uso

### 1️⃣ **Autenticación**

- **Registro**: `POST /api/v1/auth/register`
- **Login**: `POST /api/v1/auth/login`

### 2️⃣ **Gestión de Usuarios**

- **Ver perfil propio**: `GET /api/v1/user/me`
- **Buscar usuarios**: `GET /api/v1/user/search?name={nombre}`
- **Ver perfil público**: `GET /api/v1/user/profile/{nombre}`
- **Obtener perfiles por id**: `GET /api/v1/user/{userId}`
- **Borrar usuario**: `DELETE /api/v1/user/{userId}`

### 3️⃣ **Sistema de Seguimiento**

- **Seguir usuario**: `POST /api/v1/user/follow/{userId}`
- **Lista de seguidos**: `GET /api/v1/user/follow/{userId}`
- **Lista de seguidores**: `GET /api/v1/user/followers/{userId}`
- **Dejar de seguir**: `DELETE /api/v1/user/unfollow/{userId}`

### 4️⃣ **Gestión de Puntuaciones**

- **Ver todas las puntuaciones**: `GET /api/v1/score`
- **Añadir puntuación**: `POST /api/v1/score`
- **Ver mis puntuaciones**: `GET /api/v1/score/me`
- **Actualizar récord**: `PUT /api/v1/score`

---

## Conclusión

Este proyecto demuestra la implementación de una API REST con **Spring Boot**, gestión de seguridad con **JWT**, y el uso de bases de datos relacionales. Su despliegue en **Azure** permite acceder a los servicios desde cualquier parte.

---

## Contribuciones, agradecimientos y referencias

Agradecimientos a los profesores y compañeros del **DAM** por su apoyo en el desarrollo del proyecto.

Fuentes de referencia:

- Documentación de **Spring Boot**
- **MySQL** & JPA Docs
- **Azure for Students**

---

## Licencia

Este proyecto está bajo la **MIT License** - Ver `LICENSE.md` para más detalles.

---

## Contacto

**Teresa Charlo Millán** - [tcharlomillan@gmail.com](mailto:tcharlomillan@gmail.com)

**GitHub**: [https://github.com/teita98](https://github.com/teita98)

**LinkedIn**: [https://linkedin.com/in/teresa-charlo-millan](https://linkedin.com/in/teresa-charlo-millan)
