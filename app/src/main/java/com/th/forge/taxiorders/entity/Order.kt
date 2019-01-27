package com.th.forge.taxiorders.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.util.Date

class Order : Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("startAddress")
    @Expose
    var startAddress: Address? = null

    @SerializedName("endAddress")
    @Expose
    var endAddress: Address? = null

    @SerializedName("price")
    @Expose
    var price: Price? = null

    @SerializedName("orderTime")
    @Expose
    var orderTime: Date? = null

    @SerializedName("vehicle")
    @Expose
    var vehicle: Vehicle? = null

    fun getId(): Int {
        return id!!
    }

    fun setId(id: Int) {
        this.id = id
    }

}
