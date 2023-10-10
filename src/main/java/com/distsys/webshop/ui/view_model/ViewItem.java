package com.distsys.webshop.ui.view_model;

import java.io.Serializable;
import java.util.Objects;

public class ViewItem implements Serializable {
    private final int id;
    private final String name;
    private final double price;
    private final int stockQuantity;
    public ViewItem(int id, String name, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public int getId() {
        return id;
    }
    public double getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public int getStockQuantity() {
        return stockQuantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewItem viewItem = (ViewItem) o;
        return id == viewItem.id;
    }

    @Override
    public String toString() {
        return "ViewItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}