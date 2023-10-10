package com.distsys.webshop.db.data_access;

import com.distsys.webshop.bo.model.Order;
import com.distsys.webshop.db.management.DBManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class OrderDB extends Order {
    private static final String INSERT_ORDER = "INSERT INTO t_order (first_name, last_name, " +
            "street_name, zip_code, city, date_time, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_ORDER_WITH_USER = "INSERT INTO t_order (first_name, last_name, " +
            "street_name, zip_code, city, date_time, status, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static Order createNewOrder(Order order) throws RuntimeException{
        OrderDB.insertToOrderDB(order);
        OrderItemDB.insertToOrderItemDB(order.getId(), order.getIdQuantityMap());
        return order;
    }
    static void insertToOrderDB(Order order){
        Connection con = DBManager.getConnection();

        try(PreparedStatement ps = con.prepareStatement(order.getUserId()!=null ? INSERT_ORDER_WITH_USER : INSERT_ORDER,
                Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, order.getFirstName());
            ps.setString(2, order.getLastName());
            ps.setString(3, order.getStreetName());
            ps.setString(4, order.getZipCode());
            ps.setString(5, order.getCity());
            ps.setString(6, formatDateTime(order.getLocalDateTime()));
            ps.setString(7, order.getStatus().name());
            if(order.getUserId()!=null) ps.setString(8, order.getUserId());
            ps.executeUpdate();

            order.setId(getGeneratedOrderId(ps));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getGeneratedOrderId(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    private OrderDB(Map<Integer, Integer> itemIdsAndQuantity, String firstName, String lastName, String streetName, String zipCode, String city) {
        super(itemIdsAndQuantity, firstName, lastName, streetName, zipCode, city);
    }

    private OrderDB(String userId, Map<Integer, Integer> itemIdsAndQuantity, String firstName, String lastName, String streetName, String zipCode, String city) {
        super(userId, itemIdsAndQuantity, firstName, lastName, streetName, zipCode, city);
    }

    private static String formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
