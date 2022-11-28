package com.utn.frba.mobile.domain.models

data class EventSettingModel(
    val eventId: String,
    val userId: String,
    val notify: Boolean,
)

enum class EventSettingFields(val value: String) {
    USER_ID("owner_user_id"),
    NOTIFY("notify"),
    EVENT_ID("event_id")
}