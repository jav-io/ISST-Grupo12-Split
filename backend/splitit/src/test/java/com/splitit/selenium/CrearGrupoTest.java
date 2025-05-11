package com.splitit.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrearGrupoTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\WebDriver\\msedgedriver.exe");
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
public void testCrearGrupo() {
    driver.get("http://localhost:8080/register");

    String email = "grupo" + System.currentTimeMillis() + "@test.com";
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nombre"))).sendKeys("Usuario Grupo");
    driver.findElement(By.name("email")).sendKeys(email);
    driver.findElement(By.name("password")).sendKeys("password123");
    driver.findElement(By.cssSelector("button[type='submit']")).click();

    // Login
    wait.until(ExpectedConditions.urlContains("/login"));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys(email);
    driver.findElement(By.name("password")).sendKeys("password123");
    driver.findElement(By.cssSelector("button[type='submit']")).click();

    // Ir a crear grupo
    wait.until(ExpectedConditions.urlContains("/dashboard"));
    driver.get("http://localhost:8080/crear-grupo");

    // Rellenar formulario
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nombre"))).sendKeys("Viaje ISST");
    driver.findElement(By.id("descripcion")).sendKeys("Gastos para el viaje del grupo ISST");
    driver.findElement(By.name("miembros[0].nombre")).sendKeys("Pablo");
    driver.findElement(By.name("miembros[0].email")).sendKeys("pablo@test.com");
    driver.findElement(By.cssSelector("button[type='submit']")).click();

    // Esperar a que cargue cualquier página y comprobar contenido
    wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    String contenido = driver.getPageSource().toLowerCase();
    assertTrue(contenido.contains("viaje isst"), "El grupo no se creó correctamente");
}

}
