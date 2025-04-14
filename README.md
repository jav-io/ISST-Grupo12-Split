
<p align="center">
  <img src="imagenes/logo2.png" alt="Logo Split.it" width="300"/>
</p>

# 📘 Split.it - ISST Grupo 12
**Split.it** es una aplicación web colaborativa para la gestión de gastos compartidos entre amigos, familiares o compañeros de piso. Permite crear grupos, registrar gastos y calcular automáticamente cuánto debe pagar o recibir cada miembro, de forma sencilla, rápida y visual.

---

## ✨ Identidad Visual

La imagen de marca de Split.it está basada en un símbolo clave: el **dólar ($) dividido diagonalmente**, que representa gráficamente el concepto de "split".

- Diseño minimalista con **colores azules** que transmiten confianza y tecnología.
- ✅ El símbolo `$` integrado como primera letra en `plit.it`.

---

## ✅ MVP - Funcionalidad básica

- 📌 Creación de grupos de gasto con descripción y administrador asignado automáticamente.
- 👥 Añadir miembros a grupos y asignarles roles: `ADMIN` o `MEMBER`.
- 💳 Registrar gastos indicando:
  - Pagador
  - Importe
  - Descripción
  - Categoría
  - Fecha
---

## 🧰 Tecnologías previstas

| Parte         | Tecnología                 |
|---------------|----------------------------|
| **Frontend**  | Thymeleaf     |
| **Backend**   | Java con Spring Boot (REST API) |
| **Base de datos** | PostgreSQL             |
| Seguridad   | Spring Security             |
| **Control de versiones** | Git + GitHub     |
| **Gestión de tareas** | Trello             |
| **Metodología** | Scrum                     |

---

## 👥 Equipo

| Nombre                | Rol                  |
|------------------------|-----------------------|
| Javier de Ponte        | Product Owner         |
| Nicolás García Sobrino | Product Developer     |
| Santiago Rayán Castro  | Scrum Master          |
| Pablo Bas Iglesias     | Product Developer
| Rodrigo de la Nuez     | Product Developer     |

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
├── .gitignore                      # Archivos y directorios ignorados por Git
├── README.md                       # Documentación principal del proyecto
├── docs/                           # Documentación adicional del proyecto
└── backend/                        # Código del backend
    └── splitit/                    # Proyecto principal de Spring Boot
        ├── pom.xml                 # Configuración de Maven y dependencias
        ├── src/                    # Código fuente
        │   └── main/
        │       ├── java/com/splitit/       # Paquete principal
        │       │   ├── controller/         # Controladores REST
        │       │   ├── service/            # Servicios de negocio
        │       │   ├── repository/         # Repositorios JPA
        │       │   ├── model/              # Entidades: Usuario, Grupo, Gasto, etc.
        │       │   ├── dto/                # Clases DTO para validación de entrada
        │       │   └── config/             # Configuración de seguridad
        │       └── resources/              # Recursos de configuración
        │           ├── application.properties  # Configuración de Spring Boot
        │           ├── static/             # Recursos estáticos para frontend
        │           │   ├── css/            # Estilos CSS
        │           │   └── js/             # Lógica JavaScript
        │           └── public/             # Archivos HTML del frontend
        └── SplititApplication.java         # Punto de entrada de la app Spring Boot

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

### 📋 Modelos de datos
Un modelo es una clase Java que representa una tabla de la base de datos.

- **Usuario.java**: Entidad que representa a los usuarios registrados en el sistema. Almacena información básica como nombre, email y contraseña, y establece la relación con los grupos a través de la entidad Miembro.

- **Grupo.java**: Entidad que representa un grupo de gastos compartidos. Contiene información sobre el nombre, descripción y fecha de creación del grupo, así como las relaciones con miembros y gastos.

- **Miembro.java**: Entidad que establece la relación entre usuarios y grupos. Almacena el saldo actual del miembro en el grupo y su rol (administrador o miembro regular).

- **Gasto.java**: Entidad que representa un gasto registrado en un grupo. Contiene información sobre el monto, fecha, descripción y categoría del gasto, así como el miembro que lo pagó.

- **Deuda.java**: Entidad que representa una deuda generada por un gasto. Almacena información sobre el monto, si está saldada y la fecha de creación.


## Controladores REST

Se implementaron controladores para exponer la API REST:

- **UsuarioController**: Gestiona el registro, consulta y búsqueda de usuarios.
- **GrupoController**: Gestiona la creación, consulta y búsqueda por ID de grupos.
- **GastoController**: Permite registrar y consultar gastos.
- **MiembroController**: Permite la gestión de la relación entre usuarios y grupos.
- **DeudaController**: Gestiona el registro y consulta de deudas generadas por gastos.


### 🔧 Configuración

- **application.properties**: Archivo de configuración que define la conexión a la base de datos PostgreSQL, configuración de JPA/Hibernate, puerto del servidor y configuración de Thymeleaf.

- **pom.xml**: Archivo de configuración de Maven que define las dependencias del proyecto, incluyendo Spring Boot, PostgreSQL, Thymeleaf, Spring Security y otras herramientas necesarias.

- **SplititApplication.java**: Clase principal que inicia la aplicación Spring Boot.

Tambien se ha actualizado repository que permite el acceso a datos de la BBD. La carpeta repository/ contiene interfaces Java que heredan de JpaRepository, y permiten acceder fácilmente a la base de datos sin tener que escribir SQL.
Así separas la lógica (services) del acceso a la base de datos (repositories), y tu código queda limpio y modular.

![alt text](<Captura de pantalla 2025-04-01 a las 17.31.52.png>)


### ⚙️ Configuración de la base de datos

- Se ha creado y probado una base de datos local en PostgreSQL llamada `splitit`.
- Se ha creado el rol `postgres` con contraseña `password` y permisos suficientes.
- Se ha configurado correctamente el archivo `application.properties`.

---

✅ Backend funcional
La aplicación ya es capaz de iniciar correctamente y conectarse a la base de datos.

Se muestran logs de Hibernate y SQL en consola como verificación.

Se accede a la app vía navegador en http://localhost:8080.

Aparece la pantalla de login por defecto de Spring Security (sin usuarios definidos aún).

## ✅ Verificación de almacenamiento y recuperación de datos

Durante el desarrollo y pruebas de la aplicación, se han seguido los siguientes pasos para comprobar que los datos introducidos en los formularios se almacenan correctamente en la base de datos y se pueden recuperar sin errores.

### 🔹 Comprobación 1: Almacenamiento correcto

1. Se accede al formulario de creación o edición de gasto (`/añadir-gasto` o `/editar-gasto`).
2. Se introduce información válida (descripción, importe, fecha, pagador, participantes...).
3. Al enviar el formulario, los datos se procesan en el backend y se guardan en la base de datos PostgreSQL a través de los servicios y repositorios.
4. Se verifica el almacenamiento accediendo directamente a la base de datos:

#### Acceso por terminal (psql)

```bash
psql -U postgres -d splitit

SELECT * FROM gasto;
SELECT * FROM deuda;
SELECT * FROM miembro;


# 1. Arrancar PostgreSQL
brew services start postgresql

# 2. Iniciar la aplicación
cd backend/splitit
mvn spring-boot:run

# 3. Acceder al navegador
http://localhost:8080

# 4. Crear grupo, añadir gasto, editar gasto...

🔍 Acceder a la base de datos para consultar las tablas
bash
Copiar
Editar

psql -U postgres -d splitit

# Ver todas las tablas
\dt

# Consultar contenido de cada tabla
SELECT * FROM usuario;
SELECT * FROM grupo;
SELECT * FROM miembro;
SELECT * FROM gasto;
SELECT * FROM deuda;









