package com.distsys.webshop.bo.handlers;

import com.distsys.webshop.ui.view_model.ViewCart;
import com.distsys.webshop.ui.view_model.ViewItem;

import java.util.Map;

public class CartHandler {
    public static void handleAddItem(ViewCart viewCart, ViewItem itemToAdd) {
        viewCart.addItem(itemToAdd);
    }
    public static void handleRemoveItem(ViewCart viewCart, ViewItem itemToRemove) {
        viewCart.removeItem(itemToRemove);
    }
    public static double handleGetTotal(ViewCart viewCart) {
        double total = 0.0;
        for (Map.Entry<ViewItem, Integer> entry : viewCart.getItemQuantityMap().entrySet()) {
            ViewItem item = entry.getKey();
            int quantityInCart = entry.getValue();
            double itemTotal = item.getPrice() * quantityInCart;
            total += itemTotal;
        }
        return total;
    }

    private CartHandler(){}
}
