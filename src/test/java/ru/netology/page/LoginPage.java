package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id='login'] input");
    private SelenideElement passwordField = $("[data-test-id='password'] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement notificationError = $("[data-test-id='error-notification'] .notification__content");

    public LoginPage() {
        loginField.shouldBe(visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void invalidLogin() {
        loginField.setValue(DataHelper.generateRandomLogin());
        passwordField.setValue(DataHelper.generateRandomPassword());
        loginButton.click();
        notificationError.shouldBe(visible);
    }

    public void checkError(String expectedMessage) {
        notificationError.shouldBe(visible).shouldBe(text(expectedMessage));
    }
}
