// backend/splitit/src/main/java/com/splitit/SplititApplication.java
package com.splitit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal que inicia la aplicación Spring Boot
 * La anotación @SpringBootApplication combina:
 * - @Configuration: Marca la clase como fuente de definiciones de beans
 * - @EnableAutoConfiguration: Activa la configuración automática de Spring Boot
 * - @ComponentScan: Escanea componentes en el paquete actual y subpaquetes
 */
@SpringBootApplication
public class SplititApplication {
    /**
     * Método principal que inicia la aplicación
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        // Inicia la aplicación Spring Boot
        SpringApplication.run(SplititApplication.class, args);
    }
}

