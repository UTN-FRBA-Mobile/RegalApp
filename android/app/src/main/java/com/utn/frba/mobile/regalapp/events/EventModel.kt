package com.utn.frba.mobile.regalapp.events

data class EventModel(
    val name: String,
    val items: List<ItemModel> = mutableListOf<ItemModel>()
) {
//    var due_date = due_date;
//    var photo = photo;
}
