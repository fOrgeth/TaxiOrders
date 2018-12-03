package com.th.forge.taxiorders.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Price implements Serializable {
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("currency")
    @Expose
    private String currency;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
