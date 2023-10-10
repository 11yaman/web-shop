package com.distsys.webshop.bo.model;
import com.distsys.webshop.bo.model.enums.OrderStatus;
import com.distsys.webshop.db.data_access.OrderDB;

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
    private String total;
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


    public static Order newOrder(String userId, Map<Integer, Integer> itemIdsAndQuantity, String firstName,
                                 String lastName, String streetName, String zipCode, String city) {
        return OrderDB.createNewOrder(new Order(userId, itemIdsAndQuantity, firstName,
                lastName, streetName, zipCode, city));
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

    public String getTotal() {
        return total;
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