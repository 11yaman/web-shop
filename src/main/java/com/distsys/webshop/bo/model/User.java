package com.distsys.webshop.bo.model;

import com.distsys.webshop.bo.model.enums.UserRole;
import com.distsys.webshop.db.data_access.ItemDB;
import com.distsys.webshop.db.data_access.SearchType;
import com.distsys.webshop.db.data_access.UserDB;

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
        return UserDB.authenticate(userId, password);
    }

    public static boolean register(String userId, String password, String firstName, String lastName, UserRole role) {
        User user = new User(userId, password, firstName, lastName, role);
        return UserDB.createNewUser(user, password);
    }

    public static Boolean editUser(String userId, String firstName, String lastName, UserRole role) {
        return UserDB.updateUser(new User(userId, firstName, lastName, role));
    }

    public static User getUserById(String userId) {
        return UserDB.findById(userId);
    }

    public static List<User> getAllUsers() {
        return UserDB.findAllUsers();
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
