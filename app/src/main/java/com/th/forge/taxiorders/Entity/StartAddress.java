package com.th.forge.taxiorders.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartAddress {

    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("address")
    @Expose
    private String address;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
