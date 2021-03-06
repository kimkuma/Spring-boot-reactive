package com.greglturnquist.hackingspringboot.reactive.domain.commerce;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Objects;

public class Item {
    @Id
    private String id;

    private String name;

    private String description;

    private double price;

    private String distributorRegion;

    private Date releaseDate;

    private int availableUnits;

    //private Point location;

    private boolean active;

    private Item(){}

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Item(String id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Item(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistributorRegion() {
        return distributorRegion;
    }

    public void setDistributorRegion(String distributorRegion) {
        this.distributorRegion = distributorRegion;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getAvailableUnits() {
        return availableUnits;
    }

    public void setAvailableUnits(int availableUnits) {
        this.availableUnits = availableUnits;
    }

//    public Point getLocation() {
//        return location;
//    }
//
//    public void setLocation(Point location) {
//        this.location = location;
//    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.getPrice(), getPrice()) == 0 && getAvailableUnits() == item.getAvailableUnits() && isActive() == item.isActive() && Objects.equals(getId(), item.getId()) && Objects.equals(getName(), item.getName()) && Objects.equals(getDescription(), item.getDescription()) && Objects.equals(getDistributorRegion(), item.getDistributorRegion()) && Objects.equals(getReleaseDate(), item.getReleaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getPrice(), getDistributorRegion(), getReleaseDate(), getAvailableUnits(), isActive());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", distributorRegion='" + distributorRegion + '\'' +
                ", releaseDate=" + releaseDate +
                ", availableUnits=" + availableUnits +
                //", location=" + location +
                ", active=" + active +
                '}';
    }
}
