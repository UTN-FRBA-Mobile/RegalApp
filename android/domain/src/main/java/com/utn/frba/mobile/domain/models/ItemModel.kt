package com.utn.frba.mobile.domain.models

data class ItemModel(
    val name: String,
    val quantity: Int = 0,
    val price: Float? = 0F,
    val location: String? = "", //Por ahora lo manejamos como String hasta que definamos como usarlo
    val status: Boolean = false,
//    val purchasedDate: Date?
    val boughtBy: String = "",
) {}
