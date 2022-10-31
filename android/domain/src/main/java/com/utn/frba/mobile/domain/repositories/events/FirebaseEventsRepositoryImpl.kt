package com.utn.frba.mobile.domain.repositories.events

import android.content.ClipData.Item
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.squareup.anvil.annotations.ContributesBinding
import com.utn.frba.mobile.domain.DBCollections
import com.utn.frba.mobile.domain.dataStore.UserDataStore
import com.utn.frba.mobile.domain.di.AppScope
import com.utn.frba.mobile.domain.models.*
import com.utn.frba.mobile.domain.repositories.auth.UserRepository
import com.utn.frba.mobile.domain.utils.FirestoreHelper
import com.utn.frba.mobile.domain.utils.safeCall
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ContributesBinding(AppScope::class)
@AppScope
class FirebaseEventsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val helper: FirestoreHelper,
    private val userDataStore: UserDataStore
) : EventsRepository {
    override suspend fun fetchUserEvents(userId: String): NetworkResponse<List<EventModel>> =
        safeCall {
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

    override suspend fun createEvent(data: Map<String, Any>): NetworkResponse<EventModel> = safeCall {
        val result = getEventsCollection()
            .add(data)
            .await()
        val eventSnapshot = result.get().await()
        val eventModel = helper.mapDocumentToEventModel(eventSnapshot)
        return NetworkResponse.Success(eventModel)
    }
}