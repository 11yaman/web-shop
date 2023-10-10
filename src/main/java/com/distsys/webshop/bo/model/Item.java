package com.distsys.webshop.bo.model;


import com.distsys.webshop.db.data_access.ItemDB;
import com.distsys.webshop.db.data_access.SearchType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Item {
    private int id;
    private String name;
    private double price;
    private int stockQuantity;

    protected Item(int id, String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public static List<Item> getItems(SearchType searchType, String searchParam) {
        return new ArrayList<>(ItemDB.findBy(searchType, searchParam));
    }

    public static List<Item> getAllItems() {
        return new ArrayList<>(ItemDB.findBy(SearchType.ANY, ""));

    }

    public static Item getItemById(int id) {
        try {
            return ItemDB.findBy(SearchType.ID, String.valueOf(id)).get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static void updateStockAfterOrder(Map<Integer, Integer> idQuantityMap) {
        for (Map.Entry<Integer, Integer> entry : idQuantityMap.entrySet()) {
            ItemDB.updateItemStockQuantity(entry.getKey(), entry.getValue());
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    synchronized public double getPrice() {
        return price;
    }

    synchronized public int getStockQuantity() {
        return stockQuantity;
    }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }

}