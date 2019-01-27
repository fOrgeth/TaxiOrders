package com.th.forge.taxiorders.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Address : Serializable {

    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("address")
    @Expose
    var address: String? = null


}
