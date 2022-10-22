package com.utn.frba.mobile.domain.repositories.events

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.anvil.annotations.ContributesBinding
import com.utn.frba.mobile.domain.DBCollections
import com.utn.frba.mobile.domain.di.AppScope
import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.utils.safeCall
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ContributesBinding(AppScope::class)
@AppScope
class FirebaseEventsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): EventsRepository {
    override suspend fun fetchEvents(): NetworkResponse<List<EventModel>> = safeCall {
        val queryResult = getEventsCollection().get().await()
        val events =  queryResult.documents.mapNotNull {
            it.toObject(EventModel::class.java)
        }

        return NetworkResponse.Success(events)
    }

    private fun getEventsCollection(): CollectionReference {
        return db.collection(DBCollections.EVENTS.value)
    }
}