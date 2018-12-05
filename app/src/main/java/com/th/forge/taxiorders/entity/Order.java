package com.th.forge.taxiorders.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("startAddress")
    @Expose
    private Address startAddress;

    @SerializedName("endAddress")
    @Expose
    private Address endAddress;

    @SerializedName("price")
    @Expose
    private Price price;

    @SerializedName("orderTime")
    @Expose
    private Date orderTime;

    @SerializedName("vehicle")
    @Expose
    private Vehicle vehicle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(Address startAddress) {
        this.startAddress = startAddress;
    }

    public Address getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(Address endAddress) {
        this.endAddress = endAddress;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}
