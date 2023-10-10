package com.distsys.webshop.bo.handlers;

import com.distsys.webshop.bo.model.Item;
import com.distsys.webshop.db.data_access.SearchType;
import com.distsys.webshop.ui.view_model.ViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemHandler {
    public static List<ViewItem> handleGetItemsByName(String searchParam) {
        List<Item> list = Item.getItems(SearchType.NAME, searchParam);
        List<ViewItem> viewItems = new ArrayList<>();
        for (Item item: list) {
            viewItems.add(new ViewItem(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity()));
        }
        return viewItems;
    }

    public static List<ViewItem> handleGetAllItems() {
        List<Item> list = Item.getAllItems();
        List<ViewItem> viewItems = new ArrayList<>();
        for (Item item: list) {
            viewItems.add(new ViewItem(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity()));
        }
        return viewItems;
    }

    public static ViewItem handleGetItemById(int id) {
        Item item = Item.getItemById(id);
        return item==null ? null : new ViewItem(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity());
    }

    public static void handleNewOrderCreated(Map<Integer, Integer> idQuantityMap) {
        Item.updateStockAfterOrder(idQuantityMap);
    }

    private ItemHandler() {
    }
}