package com.utn.frba.mobile.domain.models

import java.util.UUID

data class ItemModel(
    val id: String = UUID.randomUUID().toString(),
    val name: String?,
    val quantity: Long? = 0,
    val price: Double? = 0.0,
    val location: String? = "", // Only name
    val latitude: Double? = null,
    val longitude: Double? = null,
    val status: Boolean? = false,
//    val purchasedDate: Date?
    val boughtBy: String? = "",
)
