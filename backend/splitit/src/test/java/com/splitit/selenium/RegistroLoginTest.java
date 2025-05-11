package com.splitit.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistroLoginTest {

    private static WebDriver driver;
    private static String email;

    @BeforeAll
    public static void setUp() {
        // Si no lo tienes en PATH, descomenta y ajusta esta línea:
        // System.setProperty("webdriver.edge.driver", "C:\\ruta\\msedgedriver.exe");
        driver = new EdgeDriver();

        // Generar un email único
        email = "selenium" + System.currentTimeMillis() + "@example.com";
    }

    @Test
    @Order(1)
    public void testRegistroUsuario() {
        driver.get("http://localhost:8080/register");

        driver.findElement(By.name("nombre")).sendKeys("Selenium Test");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("123456");

        driver.findElement(By.cssSelector("form button")).click();

        // Asegurar que redirige al login (esto puede cambiar según tu app)
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Order(2)
    public void testLoginUsuario() {
        driver.get("http://localhost:8080/login");

     // Rellenar login con el mismo email generado
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("123456");

     driver.findElement(By.cssSelector("form button")).click();

        // Esperar un momento para que se procese el login (puedes ajustar el tiempo si lo necesitas)
        try {
        Thread.sleep(1000); // puedes usar WebDriverWait para algo más fino si quieres
     } catch (InterruptedException e) {
        e.printStackTrace();
     }

     String currentUrl = driver.getCurrentUrl();
        String pageSource = driver.getPageSource();

     System.out.println("URL actual tras login: " + currentUrl);
        System.out.println("Contenido: " + pageSource);

     // Asegurarse de que NO aparece el mensaje de error
        assertFalse(currentUrl.contains("error"));
        assertFalse(pageSource.toLowerCase().contains("incorrectos"));
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
