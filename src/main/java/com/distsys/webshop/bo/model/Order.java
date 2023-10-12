package com.distsys.webshop.bo.model;
import com.distsys.webshop.bo.enums.OrderStatus;
import com.distsys.webshop.db.dao.OrderDao;

import java.time.LocalDateTime;
import java.util.*;

public class Order {
    private int id;
    private final Map<Integer, Integer> itemIdsAndQuantity;
    private String firstName;
    private String lastName;
    private String streetName;
    private String zipCode;
    private String city;
    private String userId;
    private double total;
    private LocalDateTime dateTime;
    private OrderStatus status;

    protected Order(Map<Integer, Integer> itemIdsAndQuantity, String firstName, String lastName,
                    String streetName, String zipCode, String city) {
        this.itemIdsAndQuantity = itemIdsAndQuantity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.dateTime = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
    }

    protected Order(String userId, Map<Integer, Integer> itemIdsAndQuantity, String firstName, String lastName,
                    String streetName, String zipCode, String city) {
        this.userId = userId;
        this.itemIdsAndQuantity = itemIdsAndQuantity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.dateTime = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
    }

    protected Order(int id, String firstName, String lastName, String streetName, String zipCode, String city,
                    LocalDateTime dateTime, OrderStatus status, String userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.userId = userId;
        this.dateTime = dateTime;
        this.status = status;
        this.itemIdsAndQuantity = new HashMap<>();
    }

    public static Order createNewOrder(String userId, Map<Integer, Integer> itemIdsAndQuantity, String firstName,
                                       String lastName, String streetName, String zipCode, String city) {
        Order orderToAdd = new Order(userId, itemIdsAndQuantity, firstName,
                lastName, streetName, zipCode, city);
        OrderDao.addNewOrder(orderToAdd);
        OrderItem.addOrderItems(orderToAdd);
        return orderToAdd;
    }

    public static List<Order> getUncompletedOrders() {
        return OrderDao.findUncompletedOrders();
    }

    public static boolean markOrderAsCompleted(int id) {
        return OrderDao.updateOrderAsCompleted(id);
    }

    public Map<Integer,Integer> getIdQuantityMap() {
        return new HashMap<>(itemIdsAndQuantity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getUserId() {
        return userId;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Map<Integer, Integer> getItemIdsAndQuantity() {
        return new HashMap<>(itemIdsAndQuantity);
    }

    public LocalDateTime getLocalDateTime() {
        return dateTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", itemIdsAndQuantity=" + itemIdsAndQuantity +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", userId='" + userId + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}