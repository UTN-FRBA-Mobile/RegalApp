package com.utn.frba.mobile.domain.models

data class AddEventModel(
    val name: String,
    val date: String,
    // TODO: Que tipado usaríamos para la imagen
    // val image: String = ""
)

data class EditEventModel(
    val eventId: String,
    val name: String,
    val date: String,
    // TODO: Que tipado usaríamos para la imagen
    // val image: String = ""
)
