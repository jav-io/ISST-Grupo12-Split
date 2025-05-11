package com.splitit.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

public class AnadirGastoTest {

    @Test
    public void testAnadirGasto() {
        System.setProperty("webdriver.edge.driver", "C:\\WebDriver\\msedgedriver.exe");

        WebDriver driver = new EdgeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Paso 1: Registro
            driver.get("http://localhost:8080/register");
            String randomId = UUID.randomUUID().toString().substring(0, 8);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nombre"))).sendKeys("Test" + randomId);
            driver.findElement(By.name("email")).sendKeys("test" + randomId + "@test.com");
            driver.findElement(By.name("password")).sendKeys("1234");
            driver.findElement(By.name("confirmarPassword")).sendKeys("1234"); 
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            wait.until(ExpectedConditions.urlContains("/dashboard"));

            // Paso 2: Crear grupo
            driver.get("http://localhost:8080/crear-grupo");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nombreGrupo"))).sendKeys("Grupo Test");
            driver.findElement(By.name("descripcion")).sendKeys("Grupo generado por Selenium");
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            wait.until(ExpectedConditions.urlContains("/detalle-grupo"));

            // Paso 3: Añadir gasto
            driver.findElement(By.linkText("Añadir gasto")).click(); 

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("descripcion"))).sendKeys("Cena");
            driver.findElement(By.name("monto")).sendKeys("50.00");

            // Simular selección de pagador y participantes
            WebElement pagador = driver.findElement(By.name("pagador"));
            pagador.findElement(By.tagName("option")).click(); 

            WebElement participantes = driver.findElement(By.name("participantes"));
            participantes.findElement(By.tagName("option")).click(); 

            driver.findElement(By.cssSelector("button[type='submit']")).click();

            wait.until(ExpectedConditions.urlContains("/detalle-grupo"));
            System.out.println("Gasto añadido correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falló el test");
        } finally {
            driver.quit();
        }
    }
}
