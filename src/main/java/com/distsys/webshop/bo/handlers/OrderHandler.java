package com.distsys.webshop.bo.handlers;

import com.distsys.webshop.bo.model.Order;
import com.distsys.webshop.db.management.TransactionManager;
import com.distsys.webshop.ui.view_model.ViewOrder;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ViewOrder> handleGetUncompletedOrders() {
        List<Order> uncompletedOrders = Order.getUncompletedOrders();
        List<ViewOrder> viewOrders = new ArrayList<>();

        for (Order order : uncompletedOrders) {
            ViewOrder viewOrder = new ViewOrder(
                    order.getId(),
                    order.getFirstName(),
                    order.getLastName(),
                    order.getStreetName(),
                    order.getZipCode(),
                    order.getCity(),
                    order.getLocalDateTime(),
                    order.getStatus(),
                    order.getUserId()
            );

            viewOrders.add(viewOrder);
        }

        return viewOrders;
    }

    public static boolean handlePackOrder(int id) {
        return Order.markOrderAsCompleted(id);
    }

    private OrderHandler() {}
}