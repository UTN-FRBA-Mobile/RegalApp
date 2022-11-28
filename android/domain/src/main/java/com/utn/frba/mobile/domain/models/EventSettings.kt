package com.utn.frba.mobile.domain.models


data class EventSettings(
    val eventId: String,
    val userId: String,
    val notify: Boolean,
)