package com.th.forge.taxiorders.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("startAddress")
    @Expose
    private StartAddress startAddress;

    @SerializedName("endAddress")
    @Expose
    private EndAddress endAddress;

    @SerializedName("price")
    @Expose
    private Price price;

    @SerializedName("orderTime")
    @Expose
    private String orderTime;

    @SerializedName("vehicle")
    @Expose
    private Vehicle vehicle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StartAddress getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(StartAddress startAddress) {
        this.startAddress = startAddress;
    }

    public EndAddress getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(EndAddress endAddress) {
        this.endAddress = endAddress;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
