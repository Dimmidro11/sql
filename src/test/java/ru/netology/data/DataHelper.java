package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {
    private static final Faker FAKER = new Faker();

    private DataHelper() {}

    @Value
    public static class User {
        String id;
        String login;
        String password;
        String status;
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static String generateRandomLogin() {
        return FAKER.name().username();
    }

    public static String generateRandomPassword() {
        return FAKER.internet().password();
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("petya", "123qwerty");
    }
}
