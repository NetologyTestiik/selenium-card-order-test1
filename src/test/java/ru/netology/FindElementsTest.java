package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class FindElementsTest {
    
    @Test
    void findElements() {
        open("http://localhost:9999");
        
        // Попробуем разные селекторы
        System.out.println("Trying different selectors:");
        
        // По data-test-id
        System.out.println("By [data-test-id=name]: " + $("[data-test-id=name]").exists());
        System.out.println("By [data-test-id=phone]: " + $("[data-test-id=phone]").exists());
        System.out.println("By [data-test-id=agreement]: " + $("[data-test-id=agreement]").exists());
        
        // По классам
        System.out.println("By .input: " + $(".input").exists());
        System.out.println("By input: " + $("input").exists());
        System.out.println("By button: " + $("button").exists());
        
        // По типам
        System.out.println("By input[type=text]: " + $("input[type=text]").exists());
        System.out.println("By input[type=tel]: " + $("input[type=tel]").exists());
    }
}