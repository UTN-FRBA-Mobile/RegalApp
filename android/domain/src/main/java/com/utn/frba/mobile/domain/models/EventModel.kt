package com.utn.frba.mobile.domain.models

import java.util.UUID

data class EventModel(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val items: List<ItemModel> = emptyList(),
    val date: String = "",
    // TODO: Que tipado usaríamos para la imagen
    // val image: String = "",
    val ownerId: String
)

/*
Each field name should match with the field values on the database
 */
enum class EventFields(val value: String) {
    ID("id"),
    NAME("name"),
    OWNER_ID("owner_id"),
    ITEMS("items")
}
