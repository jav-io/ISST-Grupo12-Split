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

public class ConsultarSaldoTest {

    @Test
    public void testConsultarSaldo() {
        System.setProperty("webdriver.edge.driver", "C:\\WebDriver\\msedgedriver.exe");

        WebDriver driver = new EdgeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Paso 1: Registro
            driver.get("http://localhost:8080/register");
            String randomId = UUID.randomUUID().toString().substring(0, 8);
            String nombre = "Saldo" + randomId;
            String email = "saldo" + randomId + "@test.com";

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nombre"))).sendKeys(nombre);
            driver.findElement(By.name("email")).sendKeys(email);
            driver.findElement(By.name("password")).sendKeys("1234");
            driver.findElement(By.cssSelector("form")).submit();
            wait.until(ExpectedConditions.urlContains("/login?registered"));

            // Paso 2: Login
            driver.get("http://localhost:8080/login");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys(email);
            driver.findElement(By.name("password")).sendKeys("1234");
            driver.findElement(By.cssSelector("form")).submit();
            wait.until(ExpectedConditions.urlContains("/dashboard"));

            // Paso 3: Crear grupo
            driver.get("http://localhost:8080/crear-grupo");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nombre"))).sendKeys("Grupo Saldo");
            driver.findElement(By.name("descripcion")).sendKeys("Grupo para consultar saldos");
            driver.findElement(By.cssSelector("form")).submit();

            // Paso 4: Ir a detalles del grupo
            wait.until(ExpectedConditions.urlContains("/dashboard"));
            WebElement verDetalles = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Ver detalles")));
            verDetalles.click();

            wait.until(ExpectedConditions.urlContains("/detalle-grupo/"));
            String url = driver.getCurrentUrl();
            String grupoId = url.replaceAll(".*/detalle-grupo/(\\d+).*", "$1");

            // Paso 5: Añadir gasto
            WebElement botonAnadir = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Añadir gasto")));
            botonAnadir.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("monto"))).sendKeys("10.00");
            driver.findElement(By.name("descripcion")).sendKeys("Prueba de saldo");

            WebElement botonCategoria = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.categoria-option[onclick*='COMIDA']")));
            botonCategoria.click();

            try {
                WebElement checkbox = driver.findElement(By.cssSelector("input[name='participantes'][type='checkbox']"));
                checkbox.click();
            } catch (Exception e) {
                System.out.println("⚠ No se encontró checkbox de participante.");
            }

            driver.findElement(By.cssSelector("form")).submit();
            wait.until(ExpectedConditions.urlContains("/detalle-grupo/" + grupoId));

            // Paso 6: Consultar saldo del grupo (botón azul)
            WebElement botonSaldo = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("form[action^='/saldo-grupo/'] button.btn")));
            botonSaldo.click();

            // Paso 7: Verificar que aparece el nombre del usuario en el saldo
            WebElement saldoUsuario = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), '" + nombre + "')]")));
            System.out.println("Saldo consultado correctamente para: " + nombre);

        } finally {
            driver.quit();
        }
    }
}
