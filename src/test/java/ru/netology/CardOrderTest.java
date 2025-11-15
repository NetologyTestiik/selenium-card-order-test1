package ru.netology;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldSubmitValidForm() {
        driver.get("http://localhost:9999");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id=name] input")));

        nameInput.sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id=order-success]")));
        String text = successElement.getText();
        assertTrue(text.contains("Ваша заявка успешно отправлена"));
    }

    @Test
    void shouldShowErrorForEnglishName() {
        driver.get("http://localhost:9999");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id=name] input")));

        nameInput.sendKeys("Ivan Petrov");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        // Ждем появления ошибки и проверяем
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id=name].input_invalid .input__sub")));
        String errorText = errorElement.getText();
        assertTrue(errorText.contains("Имя и Фамилия указаны неверно") || errorText.contains("Допустимы только русские буквы"));
    }
}