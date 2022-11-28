package com.utn.frba.mobile.regalapp.notifications

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NotificationHelper {
    private val client = OkHttpClient.Builder().build()

    private const val baseUrl = "https://afternoon-atoll-99698.herokuapp.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .client(client)
            .build()
    }
}