package com.th.forge.taxiorders;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.th.forge.taxiorders.api.RoxieApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static final String ROOT_URL = "https://www.roxiemobile.ru/careers/test/";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    private static RoxieApiService api;
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = getClient(interceptor);
        if (retrofit == null) {
            getRetrofit(client);
        }
        api = retrofit.create(RoxieApiService.class);
    }

    @NonNull
    private OkHttpClient getClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
    }

    private void getRetrofit(OkHttpClient client) {
        retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(client)
                .build();
    }

    @NonNull
    private Gson getGson() {
        return new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    public static RoxieApiService getApiService() {
        return api;
    }
}
