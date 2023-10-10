package com.distsys.webshop.ui.view_model;

import com.distsys.webshop.bo.model.enums.UserRole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewUser implements Serializable {
    private final String userId;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
    private List<ViewOrder> orders;


    public ViewUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public ViewUser(String userId,  String firstName, String lastName, UserRole role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public ViewUser(String userId, String password, String firstName, String lastName, UserRole role) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
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

    public List<ViewOrder> getOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                "userId='" + userId + '\'' +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", orders=" + orders +
                '}';
    }
}