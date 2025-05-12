
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
- 🔁 Consultar saldo y transferencias sugeridas entre miembros para saldar deudas.
- 📩 Enviar recordatorios por email a deudores.

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

## 📁 `backend/`

Contiene todo el backend de la aplicación usando Java y Spring Boot.

## 📄 `pom.xml`
Archivo de configuración del proyecto para Maven: versiones, dependencias, compilación y herramientas. 

## 📄 `SplititApplication.java`
Punto de entrada de la aplicación. Arranca Spring Boot y lanza el servidor web.

---

## 📁 `src/main/java/com/splitit/`

Organiza el código Java del backend en capas:

| Carpeta        | Descripción |
|----------------|-------------|
| `controller/`  | Define la API REST (endpoints, rutas) |
| `service/`     | Lógica de negocio (validaciones, cálculos) |
| `model/`       | Entidades (tablas de base de datos) |
| `repository/`  | Acceso a datos (consultas a la base de datos) |

## 📋 Modelos de datos
Un modelo es una clase Java que representa una tabla de la base de datos.

- **Usuario.java**: Entidad que representa a los usuarios registrados en el sistema. Almacena información básica como nombre, email y contraseña, y establece la relación con los grupos a través de la entidad Miembro.

- **Grupo.java**: Entidad que representa un grupo de gastos compartidos. Contiene información sobre el nombre, descripción y fecha de creación del grupo, así como las relaciones con miembros y gastos.

- **Miembro.java**: Entidad que establece la relación entre usuarios y grupos. Almacena el saldo actual del miembro en el grupo y su rol (administrador o miembro regular).

- **Gasto.java**: Entidad que representa un gasto registrado en un grupo. Contiene información sobre el monto, fecha, descripción y categoría del gasto, así como el miembro que lo pagó.

- **Deuda.java**: Entidad que representa una deuda generada por un gasto. Almacena información sobre el monto, si está saldada y la fecha de creación.
  
---
## Estructura general de la aplicación (MVC + capas)

Split.it sigue una arquitectura en capas típica de aplicaciones web en Spring Boot, combinando el patrón MVC (Modelo-Vista-Controlador) con servicios y repositorios. A continuación se explica cada componente y cómo interactúan entre ellos:

### 1. Model

El modelo representa las entidades del sistema. Estas clases están anotadas con `@Entity` y se mapean directamente a tablas en la base de datos.

```java
@Entity
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    // otros campos, getters y setters
}
```

### 2. Repository

El repositorio proporciona acceso a la base de datos. Extiende `JpaRepository` y permite realizar operaciones CRUD sin necesidad de escribir SQL.

```java
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    // Puedes añadir métodos personalizados si lo necesitas
}
```

### 3. Service

Los servicios contienen la lógica de negocio. Se encargan de gestionar las operaciones complejas y reutilizables que involucran varias entidades o validaciones.

```java
@Service
public class GrupoService {
    @Autowired
    private GrupoRepository grupoRepository;

    public Grupo obtenerGrupoPorId(Long id) {
        return grupoRepository.findById(id).orElse(null);
    }
}
```

### 4. Controller

Los controladores son la "API" del sistema. Reciben las peticiones HTTP desde el navegador o cliente, coordinan el flujo llamando a los servicios, y devuelven una vista HTML o una respuesta JSON.

Se implementaron controladores para exponer la API REST:

- **UsuarioController**: Gestiona el registro, consulta y búsqueda de usuarios.
- **GrupoController**: Gestiona la creación, consulta y búsqueda por ID de grupos.
- **GastoController**: Permite registrar y consultar gastos.
- **MiembroController**: Permite la gestión de la relación entre usuarios y grupos.
- **DeudaController**: Gestiona el registro y consulta de deudas generadas por gastos.

  Y controladores para exponer una API web para exponer las vistas HTML a traves de Thymeleaf.
- **VistaController**: Gestiona las vistas HTML de la aplicación.


```java
@Controller
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping("/grupo/{id}")
    public String verGrupo(@PathVariable Long id, Model model) {
        Grupo grupo = grupoService.obtenerGrupoPorId(id);
        model.addAttribute("grupo", grupo);
        return "ver-grupo";
    }
}
```

### Diagrama de flujo de capas

![Captura de pantalla 2025-04-19 a las 11 34 39](https://github.com/user-attachments/assets/126c8db1-5b11-4f83-ab1a-cd36d2544002)


Este diagrama resume cómo fluye la información desde el cliente hasta la base de datos y viceversa, pasando por cada capa.

1. **El cliente** hace una petición a una ruta del sistema (por ejemplo, `/grupo/1`).
2. El **Controller** recibe la petición y llama al **Service**.
3. El **Service** realiza la lógica de negocio necesaria y consulta al **Repository**.
4. El **Repository** accede a la base de datos y devuelve una entidad **Model**.
5. El resultado se propaga de vuelta al cliente, ya sea en una vista Thymeleaf o en formato JSON si se usa `@RestController`.

Este diseño facilita la separación de responsabilidades, la escalabilidad del código y la facilidad de pruebas unitarias.


---

## 📁 `src/main/resources/`

Recursos y configuración del backend:

| Elemento                  | Función |
|---------------------------|---------|
| `application.properties`  | Configura la app: puerto, conexión a base de datos, etc. |
| `templates/`              | HTML para renderizar con Thymeleaf (si se usa) |
| `static/`                 | Archivos estáticos: CSS, JS, imágenes |

---

## 🔧 Configuración

- **application.properties**: Archivo de configuración que define la conexión a la base de datos PostgreSQL, configuración de JPA/Hibernate, puerto del servidor y configuración de Thymeleaf.

- **pom.xml**: Archivo de configuración de Maven que define las dependencias del proyecto, incluyendo Spring Boot, PostgreSQL, Thymeleaf, Spring Security y otras herramientas necesarias.

- **SplititApplication.java**: Clase principal que inicia la aplicación Spring Boot.

Tambien se ha actualizado repository que permite el acceso a datos de la BBD. La carpeta repository/ contiene interfaces Java que heredan de JpaRepository, y permiten acceder fácilmente a la base de datos sin tener que escribir SQL.
Así separas la lógica (services) del acceso a la base de datos (repositories), y tu código queda limpio y modular.

![alt text](<Captura de pantalla 2025-04-01 a las 17.31.52.png>)


## ⚙️ Configuración de la base de datos

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

```

## 🔒 Seguridad en Split.it

Split.it implementa una arquitectura de seguridad robusta siguiendo las mejores prácticas de Spring Security. A continuación se detallan los aspectos clave:

### 1. Autenticación y Autorización

- **Autenticación**: Implementada mediante Spring Security con:
  - Login personalizado en `/login`
  - Procesamiento de credenciales en `/api/usuarios/login`
  - Manejo de sesiones con `JSESSIONID`
  - Cierre de sesión seguro en `/logout`

- **Autorización**: Basada en roles:
  - `ROLE_USER`: Acceso básico a funcionalidades
  - `ROLE_ADMIN`: Acceso a funciones administrativas
  - Protección de rutas mediante `@PreAuthorize`

### 2. Protección contra vulnerabilidades OWASP

- **CSRF Protection**: Activada mediante `CookieCsrfTokenRepository`
- **XSS Protection**: Configurada en `SecurityConfig` con políticas CSP
- **SQL Injection**: Prevenida mediante JPA y consultas parametrizadas
- **Autenticación Rota**: Implementación segura con BCryptPasswordEncoder

### 3. Configuración de Seguridad

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Configuración de rutas protegidas
    // Protección CSRF
    // Políticas de seguridad de contenido
    // Gestión de sesiones
}
```

### 4. Manejo de Contraseñas

- Encriptación con BCrypt
- Migración segura de contraseñas antiguas
- Validación de credenciales en el servidor

### 5. Protección de Datos

- Encriptación de contraseñas
- Validación de entrada de datos
- Protección de rutas sensibles
- Manejo seguro de sesiones

### 6. Integración con Thymeleaf

- Uso de `sec:authorize` para control de acceso en vistas
- Protección de formularios con tokens CSRF
- Mensajes de error seguros

### 7. Buenas Prácticas Implementadas

- Separación de responsabilidades
- Validación de entrada
- Manejo seguro de sesiones
- Protección contra ataques comunes
- Logging de eventos de seguridad


## 📚 Cumplimiento de Requisitos de Seguridad (ISST) - Ejemplos de Código

### 1. Fiabilidad en Sistemas
```java
// Ejemplo de manejo de errores y validación en UsuarioService.java
@Service
public class UsuarioService {
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese correo.");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }
}
```

### 2. Ciberseguridad - Principios CLAVE
```java
// Ejemplo de encriptación y confidencialidad en SecurityConfig.java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/usuarios/**").authenticated()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
            );
        return http.build();
    }
}
```

### 3. OWASP Top 10
```java
// Ejemplo de prevención de SQL Injection en GastoRepository.java
@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    // Las consultas JPA son parametrizadas automáticamente
    @Query("SELECT g FROM Gasto g WHERE g.miembro.grupo.id = :grupoId")
    List<Gasto> findByGrupoId(@Param("grupoId") Long grupoId);
}

// Ejemplo de protección XSS en SecurityConfig.java
.headers(headers -> headers
    .contentSecurityPolicy(csp -> csp
        .policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://cdnjs.cloudflare.com;")
    )
)
```

### 4. Autenticación y Autorización
```java
// Ejemplo de autenticación en UsuarioController.java
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

// Ejemplo de autorización en AdminController.java
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @GetMapping("/migrar-contrasenas")
    public String migrarContrasenas() {
        // Lógica de migración
    }
}
```

### 5. Privacidad y RGPD
```java
// Ejemplo de minimización de datos en Usuario.java
@Entity
public class Usuario {
    @Column(unique = true)
    private String email;
    
    private String password;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();
    
    // Solo almacenamos datos necesarios
}
```

### 6. Implementación Técnica
```java
// Ejemplo de BCrypt en UsuarioService.java
@Service
public class UsuarioService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Usuario save(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }
}

// Ejemplo de CSRF en formularios HTML
<form th:action="@{/register}" method="post">
    <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
    <!-- Campos del formulario -->
</form>
```

### 7. Buenas Prácticas
```java
// Ejemplo de validación de entrada en GastoDTO.java
public class GastoDTO {
    @NotNull(message = "El importe es obligatorio")
    @Min(value = 0, message = "El importe debe ser positivo")
    private Double importe;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
}

// Ejemplo de logging de eventos en GastoService.java
@Service
public class GastoService {
    private static final Logger logger = LoggerFactory.getLogger(GastoService.class);
    
    public Gasto crearGasto(GastoDTO gastoDTO) {
        logger.info("Creando nuevo gasto: {}", gastoDTO.getDescripcion());
        // Lógica de creación
    }
}
```

### Ubicación de los archivos en el proyecto:

```
backend/splitit/src/main/java/com/splitit/
├── config/
│   └── SecurityConfig.java          # Configuración de seguridad
├── controller/
│   ├── AuthController.java          # Autenticación
│   ├── AdminController.java         # Funciones de administrador
│   └── UsuarioController.java       # Gestión de usuarios
├── service/
│   ├── UsuarioService.java          # Lógica de usuarios
│   └── GastoService.java            # Lógica de gastos
├── model/
│   └── Usuario.java                 # Entidad de usuario
├── repository/
│   └── GastoRepository.java         # Acceso a datos de gastos
└── dto/
    └── GastoDTO.java                # Validación de datos
```

