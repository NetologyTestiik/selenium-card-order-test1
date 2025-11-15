package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldSubmitValidForm() {
        driver.get("http://localhost:9999");

        WebElement nameInput = driver.findElement(By.cssSelector("input[type='text']"));
        nameInput.sendKeys("Иван Петров");

        WebElement phoneInput = driver.findElement(By.cssSelector("input[type='tel']"));
        phoneInput.sendKeys("+79211234567");

        WebElement agreementCheckbox = driver.findElement(By.cssSelector(".checkbox__box"));
        agreementCheckbox.click();

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='button']"));
        submitButton.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String pageText = driver.findElement(By.tagName("body")).getText();
        assertTrue(pageText.contains("успешно") || pageText.contains("отправлена"));
    }
}