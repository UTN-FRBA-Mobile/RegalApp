package com.utn.frba.mobile.domain.models

data class EventModel(
    val id: String,
    val name: String,
    val items: List<ItemModel> = emptyList(),
    val ownerId: String
)

/*
Each field name should match with the field values on the database
 */
enum class EventFields(val value: String) {
    NAME("name"),
    OWNER_ID("owner_id")
}
