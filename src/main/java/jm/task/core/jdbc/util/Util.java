package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());
    private static SessionFactory sessionFactory;

    private static Properties dataSourceProperties() {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("src/main/resources/database.properties"))) {
            props.load(in);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return props;
    }

    public static Connection getConnection() throws SQLException {
        Properties props = Util.dataSourceProperties();
        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");
        return DriverManager.getConnection(url, user, password);
    }

    private static Configuration hibernateConfig() {
        Properties props = Util.dataSourceProperties();
        Properties hibernateProps = new Properties();
        Configuration config = new Configuration();
        hibernateProps.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        hibernateProps.put(Environment.URL, props.getProperty("url") + "mydb?useSSL=false");
        hibernateProps.put(Environment.USER, props.getProperty("user"));
        hibernateProps.put(Environment.PASS, props.getProperty("password"));
        hibernateProps.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        hibernateProps.put(Environment.SHOW_SQL, "true");
        hibernateProps.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        hibernateProps.put(Environment.HBM2DDL_AUTO, "");
        config.addProperties(hibernateProps);
        config.addAnnotatedClass(User.class);
        return config;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration config = hibernateConfig();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties()).build();
            sessionFactory = config.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
