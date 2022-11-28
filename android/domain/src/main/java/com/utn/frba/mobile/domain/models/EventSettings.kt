package com.utn.frba.mobile.domain.models

import kotlinx.serialization.SerialName

data class EventSettings(
    @SerialName("event_id")
    val eventId: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("notify")
    val notify: Boolean
)