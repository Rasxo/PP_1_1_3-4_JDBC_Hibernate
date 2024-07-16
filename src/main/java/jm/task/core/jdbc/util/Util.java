package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USERNAME = "aleva";
    private static final String PASSWORD = "220-240";
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
