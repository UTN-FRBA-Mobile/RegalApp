package com.utn.frba.mobile.domain.models

data class EventModel(
    val id: String? = null,
    val name: String = "",
    val ownerId: String
)

enum class EventFields(val value: String) {
    NAME("NAME"),
    OWNER_ID("owner_id")
}
