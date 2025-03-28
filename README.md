
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

## 🧰 Tecnologías previstas

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

```
ISST-GRUPO12-SPLIT/
├── .gitignore              # Archivos y directorios ignorados por Git
├── README.md               # Documentación principal del proyecto
├── docs/                   # Documentación adicional del proyecto
└── backend/                # Código del backend
    └── splitit/            # Proyecto principal de Spring Boot
        ├── pom.xml         # Configuración de Maven y dependencias
        ├── SplititApplication.java  # Punto de entrada de la aplicación
        └── src/            # Código fuente
            └── main/       # Código principal
                ├── java/com/splitit/  # Paquete principal
                │   ├── controller/    # Controladores REST
                │   ├── service/       # Servicios de negocio
                │   ├── model/         # Entidades y modelos
                │   └── repository/    # Repositorios para acceso a datos
                └── resources/         # Recursos de la aplicación
                    ├── application.properties  # Configuración
                    ├── templates/     # Plantillas HTML (Thymeleaf)
                    └── static/        # Recursos estáticos
                        ├── css/       # Hojas de estilo
                        └── js/        # Scripts JavaScript

```

---

## 📄 `.gitignore`

Archivo que le indica a Git qué archivos no deben subirse. Ejemplos típicos:

```
/target/
*.class
.idea/
.vscode/
*.log
.env
```

Evita subir archivos innecesarios o sensibles.

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
```

---

## 📁 `src/main/java/com/splitit/`

Organiza el código Java del backend en capas:

| Carpeta        | Descripción |
|----------------|-------------|
| `controller/`  | Define la API REST (endpoints, rutas) |
| `service/`     | Lógica de negocio (validaciones, cálculos) |
| `model/`       | Entidades (tablas de base de datos) |
| `repository/`  | Acceso a datos (consultas a la base de datos) |

---

## 📁 `src/main/resources/`

Recursos y configuración del backend:

| Elemento                  | Función |
|---------------------------|---------|
| `application.properties`  | Configura la app: puerto, conexión a base de datos, etc. |
| `templates/`              | HTML para renderizar con Thymeleaf (si se usa) |
| `static/`                 | Archivos estáticos: CSS, JS, imágenes |

---

## ❓ ¿Por qué aparece así en VSCode?

VSCode muestra `backend/splitit` como un proyecto independiente porque:

- Spring Boot sigue la estructura estándar de Maven
- `src/main/java` es donde va el código fuente
- `resources/` incluye la configuración y archivos web

Esta convención facilita la integración con Spring y las herramientas de Java.

---

## 🔄 Avances del Sprint 2

Durante este sprint, hemos implementado los siguientes componentes:

### 📋 Modelos de datos

- **Usuario.java**: Entidad que representa a los usuarios registrados en el sistema. Almacena información básica como nombre, email y contraseña, y establece la relación con los grupos a través de la entidad Miembro.

- **Grupo.java**: Entidad que representa un grupo de gastos compartidos. Contiene información sobre el nombre, descripción y fecha de creación del grupo, así como las relaciones con miembros y gastos.

- **Miembro.java**: Entidad que establece la relación entre usuarios y grupos. Almacena el saldo actual del miembro en el grupo y su rol (administrador o miembro regular).

- **Gasto.java**: Entidad que representa un gasto registrado en un grupo. Contiene información sobre el monto, fecha, descripción y categoría del gasto, así como el miembro que lo pagó.

- **Deuda.java**: Entidad que representa una deuda generada por un gasto. Almacena información sobre el monto, si está saldada y la fecha de creación.

### 🔧 Configuración

- **application.properties**: Archivo de configuración que define la conexión a la base de datos PostgreSQL, configuración de JPA/Hibernate, puerto del servidor y configuración de Thymeleaf.

- **pom.xml**: Archivo de configuración de Maven que define las dependencias del proyecto, incluyendo Spring Boot, PostgreSQL, Thymeleaf, Spring Security y otras herramientas necesarias.

- **SplititApplication.java**: Clase principal que inicia la aplicación Spring Boot.

### 📌 Próximos pasos

Para completar el Sprint 2, necesitamos implementar:

1. Repositorios JPA para acceso a datos
2. Servicios para la lógica de negocio
3. Controladores REST para exponer la API
4. Plantillas HTML básicas para la interfaz de usuario
5. Configuración de seguridad básica

