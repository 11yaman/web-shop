package com.distsys.webshop.bo.model;

import com.distsys.webshop.bo.enums.UserRole;
import com.distsys.webshop.db.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String userId;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
    private List<Order> orders;

    protected User(String userId, String firstName, String lastName, UserRole role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    protected User(String userId, String password, String firstName, String lastName, UserRole role) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public static User login(String userId, String password) {
        return UserDao.authenticate(userId, password);
    }

    public static boolean register(String userId, String password, String firstName, String lastName, UserRole role) {
        User user = new User(userId, password, firstName, lastName, role);
        return UserDao.createNewUser(user, password);
    }

    public static Boolean editUser(String userId, String firstName, String lastName, UserRole role) {
        return UserDao.updateUser(new User(userId, firstName, lastName, role));
    }

    public static User getUserById(String userId) {
        return UserDao.findById(userId);
    }

    public static List<User> getAllUsers() {
        return UserDao.findAllUsers();
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public List<Order> getUserOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", orders=" + orders +
                '}';
    }
}
