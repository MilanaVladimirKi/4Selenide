package ru.topacademy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.byText;

public class CardFillTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    public String createDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void testSendForm() {
        String planningDate = createDate(7, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Кузьма Петров-Водкин");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=notification] .notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(20));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + planningDate),
                Duration.ofSeconds(20));
    }
}
