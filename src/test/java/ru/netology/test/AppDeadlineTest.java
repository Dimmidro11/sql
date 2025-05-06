package ru.netology.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.*;

@Slf4j
public class AppDeadlineTest {
    private LoginPage loginPage;


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        loginPage = new LoginPage();
    }

    @Test
    @DisplayName("Вход в личный кабинет с валидными данными")
    void shouldLoginValidUserAndCode() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var dashboardPage = verificationPage.validVerification(getCode());
    }

    @Test
    @DisplayName("Попытка входа в личный кабинет валидного пользователя с невалидным кодом")
    void shouldNotLoginInvalidCode() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerification();
        verificationPage.checkError("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }

    @Test
    @DisplayName("Попытка входа в личный кабинет невалидного пользователя")
    void shouldNotLoginInvalidUser() {
        loginPage.invalidLogin();
        loginPage.checkError("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("Вход в личный кабинет валидным пользователем с валидным кодом, после невалидного кода")
    void shouldLoginValidUserValidCodeAfterInvalidCode() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerification();
        var dashboardPage = verificationPage.validVerification(getCode());
    }

    @Test
    @DisplayName("Проверка блокировки после 3-х попыток ввода невалидного кода верификации")
    void shouldBlockAfter3TimesInvalidCode() {
        var authInfo = DataHelper.getOtherAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerification();
        open("http://localhost:9999");
        verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerification();
        open("http://localhost:9999");
        verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerification();
        open("http://localhost:9999");
        verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerification();
        verificationPage.checkError("Ошибка! Превышено количество попыток ввода кода!");
    }
}
