package com.distsys.webshop.ui.view_model;

import java.io.Serializable;
import java.util.*;

public class ViewCart implements Serializable {
    private Map<ViewItem, Integer> itemQuantityMap;
    private double total;

    public ViewCart() {
        itemQuantityMap = new HashMap<>();
        total = 0.0;
    }

    public void addItem(ViewItem item) {
        if(item==null) return;
        int quantityInCart = itemQuantityMap.getOrDefault(item, 0);
        itemQuantityMap.put(item, quantityInCart+1);
        System.out.println("Added " + itemQuantityMap.get(item));
    }
    public void removeItem(ViewItem item) {
        if(item==null) return;
        int quantityInCart = itemQuantityMap.getOrDefault(item, 0);

        if (quantityInCart <= 1)
            itemQuantityMap.remove(item);
        else
            itemQuantityMap.put(item, quantityInCart-1);
        System.out.println("Removed " + itemQuantityMap.get(item));
    }

    public Map<ViewItem, Integer> getItemQuantityMap() {
        return new HashMap<>(itemQuantityMap);
    }
    public Map<Integer, Integer> getIdQuantityMap() {
        Map<Integer, Integer> itemIdsAndQuantity = new HashMap<>();
        for (Map.Entry<ViewItem, Integer> entry : itemQuantityMap.entrySet()) {
            itemIdsAndQuantity.put(entry.getKey().getId(),entry.getValue());
        }
        return itemIdsAndQuantity;
    }

    public List<ViewItem> getItems() {
        return new ArrayList<>(itemQuantityMap.keySet());
    }

    public int getItemQuantityInCart(ViewItem item){
        return itemQuantityMap.getOrDefault(item, 0);
    }
}
