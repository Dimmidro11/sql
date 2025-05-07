package ru.netology.data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void updateUsers(String login, String password) {
        var dataSQL = "INSERT INTO users(id, login, password) VALUES (UUID(), ?, ?);";
        try (var conn = getConnection()) {
            runner.update(conn, dataSQL, login, password);
        }
    }

    @SneakyThrows
    public static String getCode() {
        var dataSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            return runner.query(conn, dataSQL, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static void clearTables() {
        try (var conn = getConnection()) {
            runner.execute(conn, "DELETE FROM auth_codes;");
            runner.execute(conn, "DELETE FROM card_transactions;");
            runner.execute(conn, "DELETE FROM cards;");
            runner.execute(conn, "DELETE FROM users;");
        }
    }

    @SneakyThrows
    public static void clearCodes() {
        try (var conn = getConnection()) {
            runner.execute(conn, "DELETE FROM auth_codes;");
        }
    }
}
