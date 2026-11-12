# Gym Digital - Backend API

API RESTful robusta y segura desarrollada para la gestión administrativa de un gimnasio moderno. Este servicio provee la lógica de negocio, persistencia de datos y seguridad para la plataforma "Gym Digital".

## Tecnologías Principales
* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3
* **Seguridad:** Spring Security con JSON Web Tokens (JWT)
* **Base de Datos:** PostgreSQL + Spring Data JPA
* **Documentación:** Springdoc OpenAPI (Swagger)

##  Características
* **Autenticación Segura:** Sistema de login sin estado (stateless) utilizando JWT.
* **CORS Configurado:** Habilitado para consumo desde clientes frontend modernos (Vite/React en puertos 5173/5174).
* **Gestión de Usuarios y Reservas:** Endpoints protegidos para la administración de clientes y horarios.

##  Instalación y Uso
1. Clonar el repositorio: `git clone https://github.com/JahirMedina332/gym-digital-backend.git`
2. Configurar las credenciales de PostgreSQL en `application.properties`.
3. Ejecutar la aplicación Spring Boot.
4. El servidor se levantará en el puerto **9525**.
5. Acceder a la documentación de la API en: `http://localhost:9525/swagger-ui/index.html`
