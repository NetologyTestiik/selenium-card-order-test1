package ru.netology;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class OrderCardTest {
    
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitValidForm() {
        Faker faker = new Faker(new Locale("ru"));
        
        $("[data-test-id=name] input").setValue(faker.name().fullName());
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $("button[type=button]").click();
        
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldShowErrorWhenNameIsEmpty() {
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $("button[type=button]").click();
        
        $("[data-test-id=name].input_invalid .input__sub")
            .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowErrorWhenPhoneIsEmpty() {
        Faker faker = new Faker(new Locale("ru"));
        
        $("[data-test-id=name] input").setValue(faker.name().fullName());
        $("[data-test-id=agreement]").click();
        $("button[type=button]").click();
        
        $("[data-test-id=phone].input_invalid .input__sub")
            .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowErrorWhenNameIsInvalid() {
        $("[data-test-id=name] input").setValue("John Doe");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $("button[type=button]").click();
        
        $("[data-test-id=name].input_invalid .input__sub")
            .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldShowErrorWhenPhoneIsInvalid() {
        Faker faker = new Faker(new Locale("ru"));
        
        $("[data-test-id=name] input").setValue(faker.name().fullName());
        $("[data-test-id=phone] input").setValue("89991234567");
        $("[data-test-id=agreement]").click();
        $("button[type=button]").click();
        
        $("[data-test-id=phone].input_invalid .input__sub")
            .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldShowErrorWhenCheckboxNotChecked() {
        Faker faker = new Faker(new Locale("ru"));
        
        $("[data-test-id=name] input").setValue(faker.name().fullName());
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("button[type=button]").click();
        
        $("[data-test-id=agreement].input_invalid .checkbox__text")
            .shouldHave(Condition.text("Я соглашаюсь с условиями обработки"));
    }
}