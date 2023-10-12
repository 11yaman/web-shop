package com.distsys.webshop.bo.handlers;

import com.distsys.webshop.ui.viewmodel.CartDto;
import com.distsys.webshop.ui.viewmodel.ItemDto;

import java.util.Map;

public class CartHandler {
    public static void handleAddItem(CartDto cartDto, ItemDto itemToAdd) {
        cartDto.addItem(itemToAdd);
    }
    public static void handleRemoveItem(CartDto cartDto, ItemDto itemToRemove) {
        cartDto.removeItem(itemToRemove);
    }
    public static double handleGetTotal(CartDto cartDto) {
        double total = 0.0;
        for (Map.Entry<ItemDto, Integer> entry : cartDto.getItemQuantityMap().entrySet()) {
            ItemDto item = entry.getKey();
            int quantityInCart = entry.getValue();
            double itemTotal = item.getPrice() * quantityInCart;
            total += itemTotal;
        }
        return total;
    }

    private CartHandler(){}
}
