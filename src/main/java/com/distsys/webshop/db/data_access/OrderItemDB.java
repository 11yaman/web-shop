package com.distsys.webshop.db.data_access;

import com.distsys.webshop.db.management.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class OrderItemDB {
    private static final String INSERT_ORDER_ITEM =
            "INSERT INTO t_order_item (order_id, item_id, quantity) VALUES (?, ?, ?)";
    static void insertToOrderItemDB(int orderId, Map<Integer, Integer> itemIdQuantityMap) throws RuntimeException{
        Connection con = DBManager.getConnection();

        try(PreparedStatement ps = con.prepareStatement(INSERT_ORDER_ITEM)){
            for (Map.Entry<Integer,Integer> row: itemIdQuantityMap.entrySet()) {
                ps.setInt(1, orderId);
                ps.setInt(2, row.getKey());
                ps.setInt(3, row.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private OrderItemDB(){}
}
