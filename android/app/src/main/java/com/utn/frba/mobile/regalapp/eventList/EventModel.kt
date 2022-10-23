package com.utn.frba.mobile.regalapp.eventList

import com.utn.frba.mobile.regalapp.items.ItemModel

data class EventModel(
    val name: String,
    val items: List<ItemModel> = mutableListOf<ItemModel>()
) {
//    var due_date = due_date;
//    var photo = photo;
}
