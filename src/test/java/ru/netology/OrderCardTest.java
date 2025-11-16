package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class OrderCardTest {
    
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitValidForm() {
        $("[data-test-id=name]").$("input").setValue("Ivan Ivanov");
        $("[data-test-id=phone]").$("input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $("button").click();
        
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldShowErrorWhenNameIsEmpty() {
        $("[data-test-id=phone]").$("input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $("button").click();
        
        $("[data-test-id=name].input_invalid .input__sub")
            .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowErrorWhenPhoneIsEmpty() {
        $("[data-test-id=name]").$("input").setValue("Ivan Ivanov");
        $("[data-test-id=agreement]").click();
        $("button").click();
        
        $("[data-test-id=phone].input_invalid .input__sub")
            .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowErrorWhenNameIsInvalid() {
        $("[data-test-id=name]").$("input").setValue("John Doe");
        $("[data-test-id=phone]").$("input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $("button").click();
        
        $("[data-test-id=name].input_invalid .input__sub")
            .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldShowErrorWhenPhoneIsInvalid() {
        $("[data-test-id=name]").$("input").setValue("Ivan Ivanov");
        $("[data-test-id=phone]").$("input").setValue("89991234567");
        $("[data-test-id=agreement]").click();
        $("button").click();
        
        $("[data-test-id=phone].input_invalid .input__sub")
            .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldShowErrorWhenCheckboxNotChecked() {
        $("[data-test-id=name]").$("input").setValue("Ivan Ivanov");
        $("[data-test-id=phone]").$("input").setValue("+79991234567");
        $("button").click();
        
        $("[data-test-id=agreement].input_invalid .checkbox__text")
            .shouldHave(Condition.text("Я соглашаюсь с условиями обработки"));
    }
}