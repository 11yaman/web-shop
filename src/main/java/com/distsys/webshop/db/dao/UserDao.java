package com.distsys.webshop.db.dao;

import com.distsys.webshop.bo.model.User;
import com.distsys.webshop.bo.enums.UserRole;
import com.distsys.webshop.db.managers.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends User {
    private final static String SELECT_ALL = "SELECT * FROM t_user";
    private final static String SELECT_USER = "SELECT * FROM t_user WHERE id = ? AND password = ?";
    private final static String INSERT_USER = "INSERT INTO t_user (first_name, last_name, id, password, role) " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_USER = "UPDATE t_user SET first_name = ?, last_name = ?, role = ? WHERE id = ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM t_user WHERE id = ?";


    public static UserDao authenticate(String userId, String password) {

        Connection con = DbManager.getConnection();

        try (PreparedStatement ps = con.prepareStatement(SELECT_USER)) {
            ps.setString(1, userId);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String userRole = rs.getString("role");
                return new UserDao(userId, password, firstName, lastName, UserRole.valueOf(userRole));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean createNewUser(User user, String password) {
        Connection connection = DbManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(INSERT_USER)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserId());
            ps.setString(4, password);
            ps.setString(5, user.getRole().name());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<User> findAllUsers() {
        Connection con = DbManager.getConnection();
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String userRole = rs.getString("role");

                User user = new UserDao(userId, firstName, lastName, UserRole.valueOf(userRole));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static Boolean updateUser(User user) {
        Connection con = DbManager.getConnection();

        try (PreparedStatement ps = con.prepareStatement(UPDATE_USER)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getRole().name());
            ps.setString(4, user.getUserId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User findById(String userId) {
        Connection con = DbManager.getConnection();
        User user = null;

        try (PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_ID)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String userRole = rs.getString("role");

                user = new UserDao(userId, firstName, lastName, UserRole.valueOf(userRole));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public UserDao(String id, String password, String firstName, String lastName, UserRole role) {
        super(id, password, firstName, lastName, role);
    }
    public UserDao(String id, String firstName, String lastName, UserRole role) {
        super(id, firstName, lastName, role);
    }

}
