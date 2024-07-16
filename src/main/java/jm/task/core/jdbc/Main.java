package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Петр", "Петров", (byte) 25);
        userService.saveUser("Иван", "Иванов", (byte) 30);
        userService.saveUser("Александр", "Александров", (byte) 35);
        userService.saveUser("Сидр", "Сидоров", (byte) 45);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
