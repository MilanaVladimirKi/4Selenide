package ru.topacademy;

import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.byText;

public class CardFillTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }
}
