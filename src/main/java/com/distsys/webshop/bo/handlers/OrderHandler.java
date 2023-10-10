package com.distsys.webshop.bo.handlers;

import com.distsys.webshop.bo.model.Order;
import com.distsys.webshop.db.management.TransactionManager;
import com.distsys.webshop.ui.view_model.ViewOrder;

public class OrderHandler {
    public static int handleNewOrder(ViewOrder newOrder) {
        try {
            TransactionManager.begin();
            Order order = Order.newOrder(newOrder.getUserId(), newOrder.getIdQuantityMap(), newOrder.getFirstName(),
                    newOrder.getLastName(), newOrder.getStreetName(), newOrder.getZipCode(), newOrder.getCity());
            ItemHandler.handleNewOrderCreated(order.getIdQuantityMap());
            TransactionManager.commit();
            return order.getId();
        } catch (Exception e) {
            TransactionManager.rollback();
            System.out.println("Error creating order: " + e.getMessage());
            return -1;
        }
    }

    private OrderHandler() {}

}