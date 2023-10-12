package com.distsys.webshop.ui.viewmodel;

import java.io.Serializable;
import java.util.*;

public class CartDto implements Serializable {
    private Map<ItemDto, Integer> itemQuantityMap;
    private double total;

    public CartDto() {
        itemQuantityMap = new HashMap<>();
        total = 0.0;
    }

    public void addItem(ItemDto item) {
        if(item==null) return;
        int quantityInCart = itemQuantityMap.getOrDefault(item, 0);
        itemQuantityMap.put(item, quantityInCart+1);
        System.out.println("Added " + itemQuantityMap.get(item));
    }
    public void removeItem(ItemDto item) {
        if(item==null) return;
        int quantityInCart = itemQuantityMap.getOrDefault(item, 0);

        if (quantityInCart <= 1)
            itemQuantityMap.remove(item);
        else
            itemQuantityMap.put(item, quantityInCart-1);
        System.out.println("Removed " + itemQuantityMap.get(item));
    }

    public Map<ItemDto, Integer> getItemQuantityMap() {
        return new HashMap<>(itemQuantityMap);
    }
    public Map<Integer, Integer> getIdQuantityMap() {
        Map<Integer, Integer> itemIdsAndQuantity = new HashMap<>();
        for (Map.Entry<ItemDto, Integer> entry : itemQuantityMap.entrySet()) {
            itemIdsAndQuantity.put(entry.getKey().getId(),entry.getValue());
        }
        return itemIdsAndQuantity;
    }

    public List<ItemDto> getItems() {
        return new ArrayList<>(itemQuantityMap.keySet());
    }

    public int getItemQuantityInCart(ItemDto item){
        return itemQuantityMap.getOrDefault(item, 0);
    }
}
