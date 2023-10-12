package com.distsys.webshop.db.dao;

import com.distsys.webshop.bo.model.Item;
import com.distsys.webshop.bo.enums.SearchType;
import com.distsys.webshop.db.managers.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDao extends Item {
    private static final String SELECT_ITEM  = "select id, name, price, stock_quantity from t_item";
    private static final String SELECT_ITEM_BY_NAME = SELECT_ITEM + " where name like %?%";
    private static final String SELECT_ITEM_BY_ID = SELECT_ITEM + " where id = ? ";

    public static List<ItemDao> findBy(SearchType searchType, String searchParam) throws RuntimeException{
        List<ItemDao> items = new ArrayList<>();
        Connection con = DbManager.getConnection();
        String query;
        switch (searchType){
            case ID:
                query = SELECT_ITEM_BY_ID;
                break;
            case NAME:
                query = SELECT_ITEM_BY_NAME;
                break;
            default:
                query = SELECT_ITEM;
                break;
        }

        try(PreparedStatement ps = con.prepareStatement(query)){
            if(searchType!= SearchType.ANY) ps.setString(1,  searchParam );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt( "id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int stockQuantity = rs.getInt("stock_quantity");
                items.add(new ItemDao(id, name, price, stockQuantity));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }
    public static void updateItemStockQuantity(Integer itemId, Integer quantityToDecrement) throws RuntimeException{
        String updateQuery = "UPDATE t_item SET stock_quantity = stock_quantity - ? WHERE id = ?";
        Connection con = DbManager.getConnection();
        try (PreparedStatement ps = con.prepareStatement(updateQuery)) {
            ps.setInt(1, quantityToDecrement);
            ps.setInt(2, itemId);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RuntimeException("Item not found or quantity update failed.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating item stock quantity.", e);
        }
    }

    private ItemDao(int id, String name, int price, int stockQuantity) {
        super(id, name, price, stockQuantity);
    }
}