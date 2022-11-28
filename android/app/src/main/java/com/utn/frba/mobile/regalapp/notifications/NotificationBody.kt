package com.utn.frba.mobile.regalapp.notifications

import com.google.gson.annotations.SerializedName

data class NotificationBody(
    @SerializedName("token")
    val token: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("message")
    val message: String
)