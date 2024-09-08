package ru.topacademy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

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

    @Test
    public void testCheckCity() {
        String planningDate = createDate(9, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Париж");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Кузьма Петров-Водкин");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"), Duration.ofSeconds(20));
    }

    @Test
    public void testNotCity() {
        String planningDate = createDate(3, "dd.MM.yyyy");

        $(".calendar-input__custom-control input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Кузьма Петров-Водкин");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(20));
    }

    @Test
    public void testNotDate() {
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Кузьма Петров-Водкин");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"), Duration.ofSeconds(20));
    }

    @Test
    public void testNoValName() {
        String planningDate = createDate(11, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("1731!@№# Петров-Водкин");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), Duration.ofSeconds(20));
    }

    @Test
    public void test3Name() {
        String planningDate = createDate(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Кузьма Петрович Водкин");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаны неверно"), Duration.ofSeconds(20));
    }

    @Test
    public void testNotName() {
        String planningDate = createDate(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=phone] input").setValue("+79000000000");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(20));
    }

    @Test
    public void testNoValPhone() {
        String planningDate = createDate(7, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Кузьма Петров-Водкин");
        $("[data-test-id=phone] input").setValue("+790000000");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(20));
    }

    @Test
    public void testNotPhone() {
        String planningDate = createDate(7, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Кузьма Петров-Водкин");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(20));
    }

    @Test
    public void testNotCheckBox() {
        String planningDate = createDate(7, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Кузьма Петров-Водкин");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $(".button").click();
        $("[data-test-id=agreement].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"),
                Duration.ofSeconds(20));
    }







}
