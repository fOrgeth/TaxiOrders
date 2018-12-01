package com.th.forge.taxiorders.api;

import com.th.forge.taxiorders.Entity.Order;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.http.GET;

public interface RoxieApiService {
    @GET("/careers/test/orders.json")
    Call<ArrayList<Order>> getJSON();
}
