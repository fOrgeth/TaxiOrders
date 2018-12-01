package com.th.forge.taxiorders.api;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RetroClient {

    private static Retrofit retrofit = null;
    private static final String ROOT_URL = "https://www.roxiemobile.ru/careers/test/";

    private static Retrofit getRetrofitInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        if (retrofit == null) {
            Log.d("RetroClient",ROOT_URL+" !!!!!!!!!!!!!");
            retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }

    public static RoxieApiService getApiService() {
        return getRetrofitInstance().create(RoxieApiService.class);
    }
}
