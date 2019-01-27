package com.th.forge.taxiorders.api

import com.th.forge.taxiorders.entity.Order

import java.util.ArrayList


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RoxieApiService {
    @get:GET("orders.json")
    val orders: Call<ArrayList<Order>>

    @GET("images/{imageName}")
    fun getImage(@Path("imageName") imageName: String): Call<ResponseBody>
}
