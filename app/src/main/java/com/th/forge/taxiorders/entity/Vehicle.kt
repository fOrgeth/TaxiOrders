package com.th.forge.taxiorders.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Vehicle : Serializable {

    @SerializedName("regNumber")
    @Expose
    var regNumber: String? = null
    @SerializedName("modelName")
    @Expose
    var modelName: String? = null
    @SerializedName("photo")
    @Expose
    var photo: String? = null
    @SerializedName("driverName")
    @Expose
    var driverName: String? = null
}
