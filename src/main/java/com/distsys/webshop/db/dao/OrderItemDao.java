package com.distsys.webshop.db.dao;

import com.distsys.webshop.bo.model.OrderItem;
import com.distsys.webshop.db.managers.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class OrderItemDao extends OrderItem {
    private static final String INSERT_ORDER_ITEM =
            "INSERT INTO t_order_item (order_id, item_id, quantity) VALUES (?, ?, ?)";
    public static void insertToOrderItemDB(int orderId, Map<Integer, Integer> itemIdQuantityMap) throws RuntimeException{
        Connection con = DbManager.getConnection();

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
    private OrderItemDao(int orderId, int itemId, int itemQuantity){
        super(orderId, itemId, itemQuantity);
    }
}
