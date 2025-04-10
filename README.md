
<p align="center">
  <img src="logo2.png" alt="Logo Split.it" width="300"/>
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


---

##  Pruebas durante el desarrollo

A continuación se documentan los pasos para **comprobar que el backend funciona** en cada fase del proyecto.  
Cada desarrollador debe probar estos pasos para asegurarse de que su entorno está bien configurado.
 
---

##  ¿Cómo hemos comprobado que funciona?

A continuación se detallan los pasos seguidos para arrancar la aplicación correctamente y asegurarnos de que el backend funciona en local:

1. **Base de datos PostgreSQL local**  
   Se instaló y arrancó PostgreSQL con `brew services start postgresql`.  
   Se creó una base de datos llamada `splitit` y un rol de usuario llamado `postgres` con contraseña `password`.

2. **Conexión desde Spring Boot a PostgreSQL**  
   En el archivo `application.properties` se configuró correctamente la URL de conexión, el nombre de usuario y la contraseña.  
   Spring Boot logra conectarse a la base de datos al arrancar.

3. **Arranque del backend**  
   Desde el directorio `backend/splitit`, se ejecutó el comando:

   ```bash
   mvn spring-boot:run
   ```

   Si todo está bien, en la consola aparece:

   ```
   Tomcat started on port(s): 8080 (http)
   Started SplititApplication in ...
   ```

4. **Verificación en navegador**  
   Abrimos `http://localhost:8080` y aparece la pantalla de login por defecto de Spring Security.

   ![alt text](<Captura de pantalla 2025-03-29 a las 19.33.52.png>)

### 📋 Historias de usuario cumplidas

| ID   | Historia                                                                 | Estado |
|------|--------------------------------------------------------------------------|--------|
| HU-1 | Como usuario, quiero poder crear un grupo y ser su administrador         | ✅     |
| HU-2 | Como administrador, quiero poder añadir miembros al grupo                | ✅     |
| HU-3 | Como miembro, quiero poder registrar un gasto con todos los datos clave  | ✅     |
| HU-4 | Como usuario, quiero poder ver los detalles de un grupo y sus gastos     | ✅     |
| HU-5 | Como usuario, quiero poder editar un gasto registrado                    | ✅     |



La base de datos responde a las peticiones como mostramos en el video, algunas funcionalidad e historias de usuarios se puedes ya desplegar desde el frontend como la de crear grupo. 

✅ Avances respecto al backend
Se han creado todos los controladores REST necesarios.

Se ha conectado con una base de datos PostgreSQL funcional.

Se ha verificado el guardado y recuperación de entidades.

Se utiliza correctamente Thymeleaf en frontend.

Se ha empezado a aplicar buenas prácticas de DTOs y capas de servicio.

---

💻 Interfaz de usuario
index.html: página de inicio.

dashboard.html: muestra los grupos del usuario.

crear-grupo.html: formulario para crear nuevo grupo.

detalle-grupo.html: vista con los gastos del grupo.

añadir-gasto.html: formulario para añadir un gasto.

editar-gasto.html: editar un gasto existente.

Todas las vistas están implementadas usando Thymeleaf.

---

## ⚠️ Pendiente

A parte de continuar con el Sprint3 para terminar toda la funcionalidad de la aplicación con todas las historias de usuario del Trello, debemos depurar algunas cuestiones de seguridad y de funcionalidad de la app. 

1. **Implementar registro de usuarios**  
   Actualmente, solo existe el login por defecto de Spring Security.  
   Es necesario permitir que un nuevo usuario se registre (formulario de alta, guardar en base de datos, etc.).

2. **Configurar Spring Security**  
   Ahora mismo la seguridad está activada por defecto:  
   - No podemos acceder a ninguna página sin login  
   - No hay usuarios definidos en memoria ni en base de datos  
   
   Hay dos opciones:
   - Desactivar temporalmente la seguridad para poder desarrollar (solo en local)
   - Configurar usuarios reales desde base de datos y ajustar los permisos (recomendado más adelante)
  
  3. **Que el frontend haga todas las peticiones posibles ya funcionales en el backend**

---









