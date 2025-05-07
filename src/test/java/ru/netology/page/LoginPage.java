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

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        login(authInfo.getLogin(), authInfo.getPassword());
        return new VerificationPage();
    }

    public void login(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }

    public void checkError(String expectedMessage) {
        notificationError.shouldBe(visible).shouldBe(text(expectedMessage));
    }
}
