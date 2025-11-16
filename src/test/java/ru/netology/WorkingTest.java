package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class WorkingTest {
    
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldFillForm() {
        // Правильные селекторы на основе HTML структуры
        $("[data-test-id=name]").$("input").setValue("Ivan Ivanov");
        $("[data-test-id=phone]").$("input").setValue("+79991234567");
        $("[data-test-id=agreement]").$(".checkbox__control").click();
        $("button").click();
        
        // Добавим паузу чтобы увидеть результат
        sleep(2000);
    }
}