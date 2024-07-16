package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQl = "create database if not exists mydb";
            statement.execute(SQl);
            SQl = "create table if not exists mydb.users" +
                    "(id int primary key auto_increment," +
                    " name varchar(255) not null," +
                    " lastname varchar(255) not null," +
                    " age tinyint not null)";
            statement.execute(SQl);


        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQl = "drop table if exists mydb.users";
            statement.execute(SQl);
            SQl = "drop schema if exists mydb";
            statement.execute(SQl);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into mydb.users (name, lastname, age) values (?, ?, ?)";

        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            LOGGER.log(Level.INFO, String.format("User с именем - %s добавлен в базу данных", name));

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from mydb.users where id = ?";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement statement = Util.getConnection().createStatement()) {
            String SQl = "SELECT * FROM mydb.users";
            ResultSet resultSet = statement.executeQuery(SQl);
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);

            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQl = "truncate table mydb.users";
            statement.execute(SQl);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }
}
