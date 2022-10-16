package com.utn.frba.mobile.regalapp.events

import java.util.Date

data class ItemModel(
    val name: String,
    val quantity: Int = 0,
    val price: Float = 0F,
//    val location: Object,
    val status: Boolean = false,
//    val purchasedDate: Date?
    val bought_by: String = "",
    ) {}
