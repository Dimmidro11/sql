package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private SelenideElement notificationError = $("[data-test-id='error-notification'] .notification__content");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void verification(String verificationCode) {
        codeField.press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        codeField.setValue(verificationCode);
        verifyButton.click();
    }

    public DashboardPage validVerification(String verificationCode) {
        verification(verificationCode);
        return new DashboardPage();
    }

    public void checkError(String expectedMessage) {
        notificationError.shouldBe(visible).shouldBe(text(expectedMessage));
    }
}
