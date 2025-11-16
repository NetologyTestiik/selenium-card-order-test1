package ru.netology;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class FormSubmissionTest {

    @Test
    void testFormSubmission() {
        System.out.println("=== STARTING FORM SUBMISSION TEST ===");

        open("http://localhost:9999");
        System.out.println("Page opened: " + title());

        // Заполняем форму
        $("[data-test-id=name]").$("input").setValue("Иван Иванов");
        System.out.println("Name filled");

        $("[data-test-id=phone]").$("input").setValue("+79991234567");
        System.out.println("Phone filled");

        $("[data-test-id=agreement]").click();
        System.out.println("Checkbox clicked");

        // Делаем скриншот перед отправкой
        screenshot("before_submission");
        System.out.println("Screenshot before submission created");

        $("button").click();
        System.out.println("Button clicked");

        // Ждем и проверяем результат
        sleep(5000);
        System.out.println("After 5 seconds sleep");
        System.out.println("Current title: " + title());
        System.out.println("Current URL: " + WebDriverRunner.getWebDriver().getCurrentUrl());

        // Проверяем разные селекторы
        System.out.println("[data-test-id=order-success] exists: " + $("[data-test-id=order-success]").exists());
        System.out.println(".success exists: " + $(".success").exists());
        System.out.println(".message exists: " + $(".message").exists());
        
        // ИСПРАВЛЕННАЯ ЧАСТЬ - проверяем success message вместо h1/h2
        System.out.println("Success message: " + $("[data-test-id=order-success]").getText());

        // Делаем скриншот после отправки
        screenshot("after_submission");
        System.out.println("Screenshot after submission created");

        System.out.println("=== TEST COMPLETED ===");
    }
}