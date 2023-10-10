package com.distsys.webshop.ui.view_model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewOrder implements Serializable {
    private String userId;
    private final Map<Integer, Integer> itemIdsAndQuantity;
    private String firstName;
    private String lastName;
    private String streetName;
    private String zipCode;
    private String city;

    public ViewOrder(Map<Integer, Integer> itemIdsAndQuantity, String firstName, String lastName,
                     String streetName, String zipCode, String city) {
        this.itemIdsAndQuantity = itemIdsAndQuantity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
    }

    public ViewOrder(String userId, Map<Integer, Integer> itemIdsAndQuantity, String firstName, String lastName,
                     String streetName, String zipCode, String city) {
        this.userId = userId;
        this.itemIdsAndQuantity = itemIdsAndQuantity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
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
