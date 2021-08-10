import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class SelenidePracticeTest {

    //    This is date logic
    public String date(int days, String format) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(format));
    }

    // This is logic for delete string value
    public String delete() {
        return Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldReservedDate() {
        String meetDate = date(3, "dd.MM.yyyy");
        String deleteString = delete();
        $("[data-test-id='city'] .input__box .input__control[placeholder='Город']").setValue("Санкт-Петербург");
        $("[data-test-id='date'] .input__box .input__control[placeholder='Дата встречи']")
                .setValue(deleteString);
        $("[data-test-id='date'] .input__box .input__control[placeholder='Дата встречи']")
                .setValue(String.valueOf(meetDate));
        $("[data-test-id='name'] .input__box .input__control[name='name']").setValue("Тапочек Ильяс");
        $("[data-test-id='phone'] .input__box .input__control[name='phone']").setValue("+78005553535");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText("Успешно!")).shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + meetDate));
    }
    @Test
    void shouldReservedMeetThroughSevenDays() {
        String meetDate = date(7, "dd");
        String fullMeetDate = date(7,"dd.MM.yyyy");
        $("[data-test-id='city'] .input__box .input__control[placeholder='Город']").setValue("Са");
        $(byText("Санкт-Петербург")).click();
        $("[role ='button'] .icon-button__content .icon-button__text").click();
        $(byText(meetDate)).click();
        $("[data-test-id='name'] .input__box .input__control[name='name']").setValue("Тапочек Ильяс");
        $("[data-test-id='phone'] .input__box .input__control[name='phone']").setValue("+78005553535");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText("Успешно!")).shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + fullMeetDate));
    }
}