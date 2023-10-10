package com.distsys.webshop.db.data_access;

import com.distsys.webshop.bo.model.Order;
import com.distsys.webshop.bo.model.enums.OrderStatus;
import com.distsys.webshop.db.management.DBManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDB extends Order {
    private static final String INSERT_ORDER = "INSERT INTO t_order (first_name, last_name, " +
            "street_name, zip_code, city, date_time, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_ORDER_WITH_USER = "INSERT INTO t_order (first_name, last_name, " +
            "street_name, zip_code, city, date_time, status, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_UNCOMPLETED_ORDERS = "SELECT * FROM t_order WHERE status = ?";
    private static final String UPDATE_ORDER_STATUS = "UPDATE t_order SET status = ? WHERE id = ?";


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

    public static List<Order> findUncompletedOrders() {
        List<Order> uncompletedOrders = new ArrayList<>();
        Connection con = DBManager.getConnection();

        try (PreparedStatement ps = con.prepareStatement(SELECT_UNCOMPLETED_ORDERS)) {
            ps.setString(1, OrderStatus.CREATED.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String streetName = rs.getString("street_name");
                    String zipCode = rs.getString("zip_code");
                    String city = rs.getString("city");
                    LocalDateTime dateTime = parseDateTime(rs.getString("date_time"));
                    OrderStatus status = OrderStatus.valueOf(rs.getString("status"));
                    String userId = rs.getString("user_id");

                    Order order = new OrderDB(orderId, firstName, lastName, streetName, zipCode, city,
                            dateTime, status, userId);
                    uncompletedOrders.add(order);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding uncompleted orders: " + e.getMessage(), e);
        }
        return uncompletedOrders;
    }

    public static boolean updateOrderAsCompleted(int id) {
        Connection con = DBManager.getConnection();
        
        try (PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_STATUS)) {

            ps.setString(1, OrderStatus.COMPLETED.name());
            ps.setInt(2, id);

            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int getGeneratedOrderId(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    private OrderDB(int id, String firstName, String lastName, String streetName, String zipCode, String city, LocalDateTime dateTime, OrderStatus status, String userId) {
        super(id, firstName, lastName, streetName, zipCode, city, dateTime, status, userId);
    }

    private static String formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    private static LocalDateTime parseDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
