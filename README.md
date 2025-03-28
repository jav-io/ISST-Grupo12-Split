
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

## 🛠️ Próximos pasos

Se van a implementar los siguientes módulos:

1. `Usuario.java` – modelo de usuario (entidad de BBDD)
2. `UsuarioRepository.java` – interfaz para acceso a los usuarios
3. `UsuarioService.java` – lógica del usuario
4. `UsuarioController.java` – API REST para gestionar usuarios

Cada clase estará bien explicada para facilitar el desarrollo en equipo.
