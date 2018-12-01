package com.th.forge.taxiorders.api;

import com.th.forge.taxiorders.Entity.Order;

import java.util.ArrayList;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RoxieApiService {
    @GET("orders.json")
    Call<ArrayList<Order>> getOrders();

    @GET("images/{imageName}")
    Call<ResponseBody> getImage(@Path("imageName") String imageName);
}
