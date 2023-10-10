package com.distsys.webshop.bo.handlers;

import com.distsys.webshop.bo.model.User;
import com.distsys.webshop.bo.model.enums.UserRole;
import com.distsys.webshop.ui.view_model.ViewUser;

import java.util.ArrayList;
import java.util.List;

public class UserHandler {
    public static ViewUser handleUserLogin(String username, String password) {
        User user = User.login(username, password);
        return user != null ?
                new ViewUser(user.getUserId(), password, user.getFirstName(), user.getLastName(), user.getRole()) : null;
    }

    public static ViewUser handleUserRegister(ViewUser viewUser, String password) {
        return User.register(viewUser.getUserId(), password, viewUser.getFirstName(), viewUser.getLastName(), UserRole.CUSTOMER) ?
                viewUser : null;
    }

    public static List<ViewUser> handleGetAllUsers() {
        List<User> users = User.getAllUsers();
        List<ViewUser> viewUsers = new ArrayList<>();
        for (User user: users) {
            viewUsers.add(new ViewUser(user.getUserId(), user.getFirstName(), user.getLastName(), user.getRole()));
        }
        return viewUsers;
    }

    private UserHandler() {}

}