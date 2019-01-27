package com.th.forge.taxiorders

import android.app.Application

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.th.forge.taxiorders.api.RoxieApiService

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    private val gson: Gson
        get() = GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .excludeFieldsWithoutExposeAnnotation()
                .create()

    override fun onCreate() {
        super.onCreate()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = getClient(interceptor)
        if (retrofit == null) {
            initRetrofit(client)
        }
        apiService = retrofit!!.create<RoxieApiService>(RoxieApiService::class.java)
    }

    private fun getClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    private fun initRetrofit(client: OkHttpClient) {
        retrofit = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
    }

    companion object {
        private val ROOT_URL = "https://www.roxiemobile.ru/careers/test/"
        private val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

        var apiService: RoxieApiService? = null
            private set
        private var retrofit: Retrofit? = null
    }
}
