package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private SelenideElement notificationError = $("[data-test-id='error-notification'] .notification__content");

    private static final Faker FAKER = new Faker();

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerification(String verificationCode) {
        codeField.press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        codeField.setValue(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidVerification() {
        codeField.press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        codeField.setValue(FAKER.code().ean8());
        verifyButton.click();
        notificationError.shouldBe(visible);
    }

    public void checkError(String expectedMessage) {
        notificationError.shouldBe(visible).shouldBe(text(expectedMessage));
    }
}
