package com.utn.frba.mobile.domain.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.squareup.anvil.annotations.ContributesBinding
import com.utn.frba.mobile.domain.di.AppScope
import com.utn.frba.mobile.domain.models.EventFields
import com.utn.frba.mobile.domain.models.EventModel
import javax.inject.Inject

interface FirestoreHelper {
    fun mapDocumentToEventModel(document: DocumentSnapshot): EventModel
}

@ContributesBinding(AppScope::class)
class FirestoreHelperImpl @Inject constructor() : FirestoreHelper {
    override fun mapDocumentToEventModel(document: DocumentSnapshot): EventModel {
        val id = document.id
        val map = document.data ?: emptyMap()
        val eventName = map[EventFields.NAME.value].mapToString()
        val ownerId = map[EventFields.OWNER_ID.value].mapToString()

        return EventModel(
            id = id,
            name = eventName,
            ownerId = ownerId
        )
    }

    private fun Any?.mapToString(): String {
        return (this as? String).orEmpty()
    }
}