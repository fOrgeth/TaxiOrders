package com.th.forge.taxiorders;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.th.forge.taxiorders.api.RoxieApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static RoxieApiService api;
    private static Retrofit retrofit;
    private static final String ROOT_URL = "https://www.roxiemobile.ru/careers/test/";

    @Override
    public void onCreate() {
        super.onCreate();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        if (retrofit == null) {
            Log.d("RetroClient", ROOT_URL + " !!!!!!!!!!!!!");
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
            api = retrofit.create(RoxieApiService.class);
        }
    }

    public static RoxieApiService getApiService() {
        return api;
    }
}
