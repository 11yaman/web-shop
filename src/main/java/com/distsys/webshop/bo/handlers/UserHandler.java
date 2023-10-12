package com.distsys.webshop.bo.handlers;

import com.distsys.webshop.bo.model.User;
import com.distsys.webshop.bo.enums.UserRole;
import com.distsys.webshop.ui.viewmodel.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserHandler {
    private UserHandler() {}
    public static UserDto handleUserLogin(String username, String password) {
        User user = User.login(username, password);
        return user != null ?
                new UserDto(user.getUserId(), password, user.getFirstName(), user.getLastName(), user.getRole()) : null;
    }

    public static UserDto handleUserRegister(UserDto viewUser, String password) {
        return User.register(viewUser.getUserId(), password, viewUser.getFirstName(), viewUser.getLastName(), UserRole.CUSTOMER) ?
                viewUser : null;
    }

    public static List<UserDto> handleGetAllUsers() {
        List<User> users = User.getAllUsers();
        List<UserDto> viewUsers = new ArrayList<>();
        for (User user: users) {
            viewUsers.add(new UserDto(user.getUserId(), user.getFirstName(), user.getLastName(), user.getRole()));
        }
        return viewUsers;
    }

    public static boolean handleEditUser(UserDto viewUser) {
        return User.editUser(viewUser.getUserId(), viewUser.getFirstName(), viewUser.getLastName(), viewUser.getRole());
    }

    public static UserDto getUserById(String userId) {
        User user = User.getUserById(userId);
        return user==null ? null : new UserDto(user.getUserId(), user.getFirstName(), user.getLastName(), user.getRole());
    }
}