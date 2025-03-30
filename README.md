# 📘 Split.it - ISST Grupo 12

**Split.it** es una aplicación web colaborativa para la gestión de gastos compartidos entre amigos, familiares o compañeros de piso. Permite crear grupos, registrar gastos y calcular automáticamente cuánto debe pagar o recibir cada miembro, de forma sencilla, rápida y visual.

---

## ✅ MVP - Funcionalidad básica

- Crear grupos de gasto
- Añadir miembros a un grupo
- Registrar gastos realizados por los usuarios
- Calcular saldos pendientes entre miembros
- Consultar historial de transacciones por grupo

---

## 🛠️ Tecnologías previstas

| Parte         | Tecnología                 |
|---------------|----------------------------|
| **Frontend**  | HTML, CSS, JavaScript      |
| **Backend**   | Java con Spring Boot (REST API) |
| **Base de datos** | PostgreSQL             |
| **UI/UX**     | Figma                      |
| **Control de versiones** | Git + GitHub     |
| **Gestión de tareas** | Trello             |
| **Metodología** | Scrum                     |

---

## 👥 Equipo

| Nombre                | Rol                  |
|------------------------|-----------------------|
| Javier de Ponte        | Product Owner         |
| Nicolás García Sobrino | Backend Developer     |
| Santiago Rayán Castro  | Scrum Master          |
| Pablo Bas Iglesias     | Frontend Developer    |
| Rodrigo de la Nuez     | QA / Documentación    |

---

## 🔗 Enlaces útiles

- 📋 [Trello del equipo](https://trello.com/invite/b/67b468b6425fb87061c9c33e/ATTI845dd93022dd44fe13a1b588264011b168664F4D/isst-grupo12-split)
- 📁 [Documentación Sprint 1](./docs/)

_Proyecto académico para la asignatura **Ingeniería de Sistemas y Servicios Telemáticos (ISST)** – ETSIT-UPM._

---

# 📁 Estructura del proyecto

Este repositorio sigue una estructura organizada según las buenas prácticas de Spring Boot + Maven para facilitar su desarrollo y mantenimiento.

ISST-GRUPO12-SPLIT/ ├── .gitignore # Archivos y directorios ignorados por Git ├── README.md # Documentación principal del proyecto ├── docs/ # Documentación adicional del proyecto └── backend/ # Código del backend └── splitit/ # Proyecto principal de Spring Boot ├── pom.xml # Configuración de Maven y dependencias ├── SplititApplication.java # Punto de entrada de la aplicación └── src/ └── main/ ├── java/com/splitit/ │ ├── controller/ # Controladores REST │ ├── service/ # Servicios de negocio │ ├── model/ # Entidades y modelos │ └── repository/ # Repositorios para acceso a datos └── resources/ ├── application.properties # Configuración ├── templates/ # Plantillas HTML (Thymeleaf) └── static/ ├── css/ # Hojas de estilo └── js/ # Scripts JavaScript


---

## 📄 `.gitignore`

Archivo que le indica a Git qué archivos no deben subirse. Ejemplos típicos:

/target/ *.class .idea/ .vscode/ *.log .env


---

## 📄 `README.md`

Este mismo archivo. Contiene toda la información básica del proyecto: descripción, estructura, tecnología, equipo, etc.

---

## 📁 `docs/`

Documentación interna del proyecto:
- Visión (VD)
- Diseño (SDD)
- Planificación (SDP)
- Feedback del profesor

---

## 📁 `backend/`

Contiene todo el backend de la aplicación usando Java y Spring Boot.

### 📄 `pom.xml`
Archivo de configuración del proyecto para Maven: versiones, dependencias, compilación.

### 📄 `SplititApplication.java`
Punto de entrada de la aplicación. Arranca Spring Boot y lanza el servidor web.

```java
@SpringBootApplication
public class SplititApplication {
    public static void main(String[] args) {
        SpringApplication.run(SplititApplication.class, args);
    }
}

📁 src/main/java/com/splitit/

Organiza el código Java del backend en capas:
Carpeta	Descripción
controller/	Define la API REST (endpoints, rutas)
service/	Lógica de negocio (validaciones, cálculos)
model/	Entidades (tablas de base de datos)
repository/	Acceso a datos (consultas a la base de datos)
📁 src/main/resources/

Recursos y configuración del backend:
Elemento	Función
application.properties	Configura la app: puerto, conexión a base de datos, etc.
templates/	HTML para renderizar con Thymeleaf (si se usa)
static/	Archivos estáticos: CSS, JS, imágenes
♻️ Avances del Sprint 2

Durante este sprint, hemos implementado los siguientes componentes:
📋 Modelos de datos

Se han creado todas las entidades del modelo de datos: Usuario, Grupo, Miembro, Gasto, Deuda.
🔧 Repositorios JPA

Se han implementado repositorios para cada entidad (UsuarioRepository, GrupoRepository, etc.) para acceder a la base de datos sin escribir SQL.
⚙️ Servicios (service/)

Se ha creado la clase UsuarioService, que encapsula la lógica de negocio relacionada con los usuarios (como comprobar si un email ya existe).
🌐 Controladores REST (controller/)

Se ha creado el endpoint POST /api/usuarios en UsuarioController para permitir registrar nuevos usuarios desde el frontend o desde herramientas de prueba.
🔐 Configuración de Spring Security

Se ha añadido una clase SecurityConfig que desactiva temporalmente la autenticación para permitir pruebas sin necesidad de login.
🛠️ Conexión con PostgreSQL

    Se instaló PostgreSQL localmente

    Se creó la base de datos splitit

    Se configuró application.properties con la URL, usuario y contraseña

    Se verificó el arranque correcto de Spring Boot y la creación automática de las tablas

✅ Pruebas manuales

    Se realizaron pruebas manuales con Postman para verificar que el endpoint de creación de usuarios funciona correctamente.

⚠️ Pendiente

    Añadir más endpoints REST para grupos, gastos, miembros y deudas

    Completar servicios de lógica de negocio para gastos y saldos

    Implementar el login y autenticación real con Spring Security y base de datos

    Añadir validación de campos (email, contraseña, etc.) con anotaciones de Spring Validation

    Realizar pruebas unitarias y de integración

    Conectar el frontend con los endpoints del backend

