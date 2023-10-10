package com.distsys.webshop.ui.view_model;

import com.distsys.webshop.bo.model.enums.OrderStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewOrder implements Serializable {
    private double total;
    private String userId;
    private final Map<Integer, Integer> itemIdsAndQuantity;
    private int id;
    private String firstName;
    private String lastName;
    private String streetName;
    private String zipCode;
    private String city;
    private OrderStatus status;
    private LocalDateTime dateTime;

    public ViewOrder(String userId, Map<Integer, Integer> itemIdsAndQuantity, String firstName, String lastName,
                     String streetName, String zipCode, String city) {
        this.userId = userId;
        this.itemIdsAndQuantity = itemIdsAndQuantity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.total = 0.0;
    }

    public ViewOrder(int id, String firstName, String lastName, String streetName, String zipCode,
                     String city, LocalDateTime dateTime, OrderStatus status, String userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.dateTime = dateTime;
        this.status = status;
        this.userId = userId;
        this.itemIdsAndQuantity = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Integer> getIdQuantityMap() {
        return new HashMap<>(itemIdsAndQuantity);
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

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "ViewOrder{" +
                "itemIdsAndQuantity=" + itemIdsAndQuantity +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
