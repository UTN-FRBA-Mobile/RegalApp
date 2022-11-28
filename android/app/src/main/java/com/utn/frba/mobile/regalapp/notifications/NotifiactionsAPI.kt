package com.utn.frba.mobile.regalapp.notifications


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface NotifiactionsAPI {

    @Headers("Content-Type: application/json")
    @POST("/regalapp/send_message")
    fun sendNotification(
        @Body
        body: NotificationBody
    ) : Call<String>
}