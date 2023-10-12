package com.distsys.webshop.bo.handlers;

import com.distsys.webshop.bo.model.Order;
import com.distsys.webshop.db.managers.TransactionManager;
import com.distsys.webshop.ui.viewmodel.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class OrderHandler {
    public static int handleNewOrder(OrderDto newOrder) {
        try {
            TransactionManager.begin();
            Order order = Order.createNewOrder(newOrder.getUserId(), newOrder.getIdQuantityMap(), newOrder.getFirstName(),
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

    public static List<OrderDto> handleGetUncompletedOrders() {
        List<Order> uncompletedOrders = Order.getUncompletedOrders();
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : uncompletedOrders) {
            OrderDto orderDto = new OrderDto(
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

            orderDtos.add(orderDto);
        }

        return orderDtos;
    }

    public static boolean handlePackOrder(int id) {
        return Order.markOrderAsCompleted(id);
    }

    private OrderHandler() {}
}