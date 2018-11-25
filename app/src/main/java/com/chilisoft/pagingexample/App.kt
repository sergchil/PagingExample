package com.chilisoft.pagingexample

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {

    // DEPENDENCIES
    private val okHttpClient: OkHttpClient by lazy {
        val okHttpBuilder = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }

        okHttpBuilder
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                            .addHeader("X-Api-Key", "35454ae809cb4e1dbfe0bb8fc074d684")
                            .build()
                    chain.proceed(newRequest)
                }
                .addInterceptor(logging)
                .build()
    }
    private val gson: Gson by lazy {
        GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setPrettyPrinting()
                .create()
    }
    val retorfit: Retrofit by lazy {

        val url = "https://newsapi.org/v2/"

        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(url)
                .client(okHttpClient)
                .build()
    }

    // SERVICES
    val apiService: ApiService by lazy {
        ApiService.create()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
