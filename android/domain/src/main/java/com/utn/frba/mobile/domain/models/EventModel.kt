package com.utn.frba.mobile.domain.models

data class EventModel(
    val id: String? = null,
    val name: String = "",
    val ownerId: String
)

/*
Each field name should match with the field values on the database
 */
enum class EventFields(val value: String) {
    NAME("name"),
    OWNER_ID("owner_id")
}
