package com.distsys.webshop.bo.model;

import com.distsys.webshop.db.dao.OrderItemDao;

public class OrderItem {
    private int orderId;
    private int itemId;
    private int itemQuantity;

    public static void addOrderItems(Order order){
        OrderItemDao.insertToOrderItemDB(order.getId(), order.getIdQuantityMap());
    }

    protected OrderItem(int orderId, int itemId, int itemQuantity) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderId=" + orderId +
                ", itemId=" + itemId +
                ", itemQuantity=" + itemQuantity +
                '}';
    }
}
