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
            String nombre = "Test" + randomId;
            String email = "test" + randomId + "@test.com";

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
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nombre"))).sendKeys("Grupo Selenium");
            driver.findElement(By.name("descripcion")).sendKeys("Grupo de prueba Selenium");
            driver.findElement(By.cssSelector("form")).submit();

            // Paso 4: Acceder a detalles del grupo
            wait.until(ExpectedConditions.urlContains("/dashboard"));
            WebElement verDetalles = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Ver detalles")));
            verDetalles.click();

            wait.until(ExpectedConditions.urlContains("/detalle-grupo/"));
            String url = driver.getCurrentUrl();
            String grupoId = url.replaceAll(".*/detalle-grupo/(\\d+).*", "$1");

            // Paso 5: Clic en "Añadir gasto"
            WebElement botonAnadir = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Añadir gasto")));
            botonAnadir.click();

            // Paso 6: Completar y enviar formulario de gasto
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("monto"))).sendKeys("25.00");
            driver.findElement(By.name("descripcion")).sendKeys("Comida compartida");

            // Clic en botón de categoría "Comida"
            WebElement botonCategoria = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.categoria-option[onclick*='COMIDA']")));
            botonCategoria.click();

            // Seleccionar participante si aparece
            try {
                WebElement checkbox = driver.findElement(By.cssSelector("input[name='participantes'][type='checkbox']"));
                checkbox.click();
            } catch (Exception e) {
                System.out.println("⚠ No se encontró checkbox de participante.");
            }

            // Enviar formulario
            driver.findElement(By.cssSelector("form")).submit();

            // Esperar a volver a la vista detalle del grupo
            wait.until(ExpectedConditions.urlContains("/detalle-grupo/" + grupoId));

            // Verificar visualmente que el gasto se muestra en pantalla
            WebElement gasto = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'Comida compartida')]")));
            System.out.println("✅ Gasto visualizado correctamente: " + gasto.getText());

        } finally {
            driver.quit();
        }
    }
}
