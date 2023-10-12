package com.distsys.webshop.bo.handlers;

import com.distsys.webshop.bo.model.Item;
import com.distsys.webshop.bo.enums.SearchType;
import com.distsys.webshop.ui.viewmodel.ItemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemHandler {
    public static List<ItemDto> handleGetItemsByName(String searchParam) {
        List<Item> list = Item.getItems(SearchType.NAME, searchParam);
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item: list) {
            itemDtos.add(new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity()));
        }
        return itemDtos;
    }

    public static List<ItemDto> handleGetAllItems() {
        List<Item> list = Item.getAllItems();
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item: list) {
            itemDtos.add(new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity()));
        }
        return itemDtos;
    }

    public static ItemDto handleGetItemById(int id) {
        Item item = Item.getItemById(id);
        return item==null ? null : new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity());
    }

    static void handleNewOrderCreated(Map<Integer, Integer> idQuantityMap) {
        Item.updateStockAfterOrder(idQuantityMap);
    }

    private ItemHandler() {
    }

}