package com.splitit.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistroLoginTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\WebDriver\\msedgedriver.exe");
        driver = new EdgeDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testRegistroUsuario() {
        driver.get("http://localhost:8080/register");

        String email = "registro" + System.currentTimeMillis() + "@test.com";

        // Registro
        driver.findElement(By.name("nombre")).sendKeys("Test Registro");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Esperar redirección al login
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/login"));

        String contenido = driver.getPageSource().toLowerCase();
        assertTrue(contenido.contains("registro exitoso"), "El registro no redirigió al login con mensaje de éxito");
    }

    @Test
    public void testLoginUsuario() throws InterruptedException {
        driver.get("http://localhost:8080/register");

        String email = "usuario" + System.currentTimeMillis() + "@test.com";

        // Registro
        driver.findElement(By.name("nombre")).sendKeys("Usuario Test");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Esperar redirección al login
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/login"));

        // Login
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Esperar redirección al dashboard
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/dashboard"));

        String contenido = driver.getPageSource().toLowerCase();
        assertTrue(contenido.contains("mis grupos"), "El login no redirigió correctamente al área privada");
    }
}
