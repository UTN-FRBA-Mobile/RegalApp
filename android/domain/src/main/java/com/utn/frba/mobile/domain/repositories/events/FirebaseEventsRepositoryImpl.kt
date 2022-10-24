package com.utn.frba.mobile.domain.repositories.events

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.anvil.annotations.ContributesBinding
import com.utn.frba.mobile.domain.DBCollections
import com.utn.frba.mobile.domain.di.AppScope
import com.utn.frba.mobile.domain.models.EventFields
import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.utils.FirestoreHelper
import com.utn.frba.mobile.domain.utils.safeCall
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

@ContributesBinding(AppScope::class)
@AppScope
class FirebaseEventsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val helper: FirestoreHelper
) : EventsRepository {
    override suspend fun fetchUserEvents(userId: String): NetworkResponse<List<EventModel>> = safeCall {
        val queryResult = getEventsCollection()
            .whereEqualTo(EventFields.OWNER_ID.value, userId)
            .get()
            .await()
        val events = queryResult.documents.mapNotNull {
            helper.mapDocumentToEventModel(it)
        }

        return NetworkResponse.Success(events)
    }

    private fun getEventsCollection(): CollectionReference {
        return db.collection(DBCollections.EVENTS.value)
    }
}