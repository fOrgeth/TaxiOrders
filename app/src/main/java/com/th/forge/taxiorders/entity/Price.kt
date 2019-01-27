package com.th.forge.taxiorders.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Price : Serializable {
    @SerializedName("amount")
    @Expose
    var amount: Int? = null
    @SerializedName("currency")
    @Expose
    var currency: String? = null
}
