package ru.netology;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class DebugFormTest {
    
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void debugFormSubmission() {
        System.out.println("=== FILLING FORM ===");
        
        // Заполняем форму
        $("[data-test-id=name]").$("input").setValue("Ivan Ivanov");
        $("[data-test-id=phone]").$("input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        
        System.out.println("Form filled, clicking button...");
        
        // Кликаем кнопку
        $("button").click();
        
        // Ждем и делаем скриншот
        sleep(3000);
        Selenide.screenshot("after_submission");
        
        System.out.println("=== CHECKING PAGE STATE ===");
        System.out.println("Current URL: " + WebDriverRunner.getWebDriver().getCurrentUrl());
        System.out.println("Page title: " + title());
        System.out.println("Success element exists: " + $("[data-test-id=order-success]").exists());
        
        // Проверим разные возможные элементы
        System.out.println("Any success message: " + $(".success").exists());
        System.out.println("Any message element: " + $("[data-test-id]").exists());
    }
}