package com.utn.frba.mobile.domain.models

data class EventModel(
    val id: String,
    val name: String,
    val items: List<ItemModel> = emptyList()
)
