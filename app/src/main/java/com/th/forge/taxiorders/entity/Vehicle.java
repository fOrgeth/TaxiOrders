package com.th.forge.taxiorders.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vehicle implements Serializable {

    @SerializedName("regNumber")
    @Expose
    private String regNumber;
    @SerializedName("modelName")
    @Expose
    private String modelName;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("driverName")
    @Expose
    private String driverName;

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
