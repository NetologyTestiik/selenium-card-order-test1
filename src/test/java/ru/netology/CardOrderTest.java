package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

public class CardOrderTest {
    private WebDriver driver;
    private static final String URL = "http://localhost:9999";

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
        driver.get(URL);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldSubmitFormWithValidData() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }

    @Test
    void shouldShowErrorForEnglishName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivan Petrov");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertTrue(errorText.contains("русские буквы"));
    }

    @Test
    void shouldShowErrorForNameWithSpecialCharacters() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван@Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertTrue(errorText.contains("русские буквы"));
    }

    @Test
    void shouldShowErrorForNameWithNumbers() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван123 Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertTrue(errorText.contains("русские буквы"));
    }

    @Test
    void shouldShowErrorForEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", errorText);
    }

    @Test
    void shouldShowErrorForPhoneWithoutPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", errorText);
    }

    @Test
    void shouldShowErrorForShortPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+799912345");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", errorText);
    }

    @Test
    void shouldShowErrorForLongPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+799912345678");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", errorText);
    }

    @Test
    void shouldShowErrorForPhoneWithLetters() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7999abc4567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", errorText);
    }

    @Test
    void shouldShowErrorForEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", errorText);
    }

    @Test
    void shouldShowErrorForUncheckedAgreement() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("button[type='button']")).click();

        WebElement agreementCheckbox = driver.findElement(By.cssSelector("[data-test-id='agreement']"));
        assertTrue(agreementCheckbox.getAttribute("class").contains("input_invalid"));
    }
}
