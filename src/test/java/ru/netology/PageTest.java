package ru.netology;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class PageTest {
    
    @Test
    void checkPage() {
        open("http://localhost:9999");
        System.out.println("Title: " + title());
        System.out.println("URL: " + WebDriverRunner.getWebDriver().getCurrentUrl());
        System.out.println("Page source: " + WebDriverRunner.getWebDriver().getPageSource());
        Selenide.screenshot("page");
    }
}