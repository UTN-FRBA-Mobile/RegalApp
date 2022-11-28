package com.utn.frba.mobile.domain.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.squareup.anvil.annotations.ContributesBinding
import com.utn.frba.mobile.domain.di.AppScope
import com.utn.frba.mobile.domain.models.EventFields
import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.domain.models.EventSettings
import com.utn.frba.mobile.domain.models.ItemModel
import timber.log.Timber
import javax.inject.Inject

interface FirestoreHelper {
    fun mapDocumentToEventModel(document: DocumentSnapshot): EventModel
    fun mapDocumentToEventSettings(document: DocumentSnapshot): EventSettings?

}

@ContributesBinding(AppScope::class)
class FirestoreHelperImpl @Inject constructor() : FirestoreHelper {
    override fun mapDocumentToEventModel(document: DocumentSnapshot): EventModel {
        val id = document.id
        val map = document.data ?: emptyMap()
        val eventName = map[EventFields.NAME.value].mapToString()
        val ownerId = map[EventFields.OWNER_ID.value].mapToString()
        val items = map[EventFields.ITEMS.value].mapToItemsList() ?: emptyList()
        val participants: List<String> = map[EventFields.PARTICIPANTS.value] as? ArrayList<String> ?: emptyList()

        return EventModel(
            id = id,
            name = eventName,
            ownerId = ownerId,
            items = items,
            participants = participants
        )
    }

    override fun mapDocumentToEventSettings(document: DocumentSnapshot): EventSettings? {
        return document.toObject(EventSettings::class.java)
    }

    private fun Any?.mapToItemsList(): List<ItemModel>? {
        return try {
            val rawList = this as? ArrayList<HashMap<String, Any>>
            rawList?.map { hashMap ->
                ItemModel(
                    hashMap["id"] as String,
                    hashMap["name"] as? String,
                    hashMap["quantity"] as? Long,
                    hashMap["price"] as? Double,
                    hashMap["location"] as? String,
                    hashMap["latitude"] as? Double,
                    hashMap["longitude"] as? Double,
                    hashMap["status"] as? Boolean,
                    hashMap["boughtBy"] as? String,
                )
            }
        } catch (e: Exception) {
            Timber.e("Item Model is missing some required attribute")
            null
        }
    }

    private fun Any?.mapToString(): String {
        return (this as? String).orEmpty()
    }
}
