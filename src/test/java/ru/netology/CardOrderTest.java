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
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("РРІР°РЅ РџРµС‚СЂРѕРІ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals("Р’Р°С€Р° Р·Р°СЏРІРєР° СѓСЃРїРµС€РЅРѕ РѕС‚РїСЂР°РІР»РµРЅР°! РќР°С€ РјРµРЅРµРґР¶РµСЂ СЃРІСЏР¶РµС‚СЃСЏ СЃ РІР°РјРё РІ Р±Р»РёР¶Р°Р№С€РµРµ РІСЂРµРјСЏ.", text);
    }

    @Test
    void shouldShowErrorForEnglishName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivan Petrov");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("РРјСЏ Рё Р¤Р°РјРёР»РёСЏ СѓРєР°Р·Р°РЅС‹ РЅРµРІРµСЂРЅРѕ. Р”РѕРїСѓСЃС‚РёРјС‹ С‚РѕР»СЊРєРѕ СЂСѓСЃСЃРєРёРµ Р±СѓРєРІС‹, РїСЂРѕР±РµР»С‹ Рё РґРµС„РёСЃС‹.", errorText);
    }

    @Test
    void shouldShowErrorForNameWithSpecialCharacters() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("РРІР°РЅ@РџРµС‚СЂРѕРІ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("РРјСЏ Рё Р¤Р°РјРёР»РёСЏ СѓРєР°Р·Р°РЅС‹ РЅРµРІРµСЂРЅРѕ. Р”РѕРїСѓСЃС‚РёРјС‹ С‚РѕР»СЊРєРѕ СЂСѓСЃСЃРєРёРµ Р±СѓРєРІС‹, РїСЂРѕР±РµР»С‹ Рё РґРµС„РёСЃС‹.", errorText);
    }

    @Test
    void shouldShowErrorForNameWithNumbers() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("РРІР°РЅ123 РџРµС‚СЂРѕРІ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("РРјСЏ Рё Р¤Р°РјРёР»РёСЏ СѓРєР°Р·Р°РЅС‹ РЅРµРІРµСЂРЅРѕ. Р”РѕРїСѓСЃС‚РёРјС‹ С‚РѕР»СЊРєРѕ СЂСѓСЃСЃРєРёРµ Р±СѓРєРІС‹, РїСЂРѕР±РµР»С‹ Рё РґРµС„РёСЃС‹.", errorText);
    }

    @Test
    void shouldShowErrorForEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("РџРѕР»Рµ РѕР±СЏР·Р°С‚РµР»СЊРЅРѕ РґР»СЏ Р·Р°РїРѕР»РЅРµРЅРёСЏ", errorText);
    }

    @Test
    void shouldShowErrorForPhoneWithoutPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("РРІР°РЅ РџРµС‚СЂРѕРІ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("РўРµР»РµС„РѕРЅ СѓРєР°Р·Р°РЅ РЅРµРІРµСЂРЅРѕ. Р”РѕР»Р¶РЅРѕ Р±С‹С‚СЊ 11 С†РёС„СЂ, РЅР°РїСЂРёРјРµСЂ, +79012345678.", errorText);
    }

    @Test
    void shouldShowErrorForShortPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("РРІР°РЅ РџРµС‚СЂРѕРІ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+799912345");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("РўРµР»РµС„РѕРЅ СѓРєР°Р·Р°РЅ РЅРµРІРµСЂРЅРѕ. Р”РѕР»Р¶РЅРѕ Р±С‹С‚СЊ 11 С†РёС„СЂ, РЅР°РїСЂРёРјРµСЂ, +79012345678.", errorText);
    }

    @Test
    void shouldShowErrorForLongPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("РРІР°РЅ РџРµС‚СЂРѕРІ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+799912345678");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("РўРµР»РµС„РѕРЅ СѓРєР°Р·Р°РЅ РЅРµРІРµСЂРЅРѕ. Р”РѕР»Р¶РЅРѕ Р±С‹С‚СЊ 11 С†РёС„СЂ, РЅР°РїСЂРёРјРµСЂ, +79012345678.", errorText);
    }

    @Test
    void shouldShowErrorForPhoneWithLetters() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("РРІР°РЅ РџРµС‚СЂРѕРІ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7999abc4567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("РўРµР»РµС„РѕРЅ СѓРєР°Р·Р°РЅ РЅРµРІРµСЂРЅРѕ. Р”РѕР»Р¶РЅРѕ Р±С‹С‚СЊ 11 С†РёС„СЂ, РЅР°РїСЂРёРјРµСЂ, +79012345678.", errorText);
    }

    @Test
    void shouldShowErrorForEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("РРІР°РЅ РџРµС‚СЂРѕРІ");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("РџРѕР»Рµ РѕР±СЏР·Р°С‚РµР»СЊРЅРѕ РґР»СЏ Р·Р°РїРѕР»РЅРµРЅРёСЏ", errorText);
    }

    @Test
    void shouldShowErrorForUncheckedAgreement() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("РРІР°РЅ РџРµС‚СЂРѕРІ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("button[type='button']")).click();

        String errorText = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).getText().trim();
        assertTrue(errorText.contains("РЇ СЃРѕРіР»Р°С€Р°СЋСЃСЊ"));
    }
}

