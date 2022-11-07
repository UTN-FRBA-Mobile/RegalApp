package com.utn.frba.mobile.domain.repositories.events

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.anvil.annotations.ContributesBinding
import com.utn.frba.mobile.domain.DBCollections
import com.utn.frba.mobile.domain.dataStore.UserDataStore
import com.utn.frba.mobile.domain.di.AppScope
import com.utn.frba.mobile.domain.models.EventFields
import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.domain.models.ItemModel
import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.utils.FirestoreHelper
import com.utn.frba.mobile.domain.utils.safeCall
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ContributesBinding(AppScope::class)
@AppScope
class FirebaseEventsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val helper: FirestoreHelper,
    private val userDataStore: UserDataStore
) : EventsRepository {
    override suspend fun fetchUserEvents(): NetworkResponse<List<EventModel>> =
        safeCall {
            val userId = userDataStore.getLoggedUser()?.id
            check(userId != null) {
                "User not found"
            }
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

    override suspend fun createEvent(data: Map<String, Any>): NetworkResponse<EventModel> =
        safeCall {
            val result = getEventsCollection()
                .add(data)
                .await()
            val eventSnapshot = result.get().await()
            val eventModel = helper.mapDocumentToEventModel(eventSnapshot)
            return NetworkResponse.Success(eventModel)
        }

    override suspend fun addItems(
        eventId: String,
        items: List<ItemModel>
    ): NetworkResponse<EventModel> = safeCall {
        val eventRef = getEventsCollection()
            .document(eventId)

        val currentModel = helper.mapDocumentToEventModel(eventRef.get().await())
        val newList = currentModel.items + items
        eventRef.update(EventFields.ITEMS.value, newList).await()
        NetworkResponse.Success(helper.mapDocumentToEventModel(eventRef.get().await()))
    }

    override suspend fun editItem(eventId: String, itemId: String, model: ItemModel) {

        val currentModel = getEventModel(eventId)
        val newItems = currentModel.items.map {
            if (it.id == eventId) {
                model
            } else {
                it
            }
        }
        getEventDocumentReference(eventId).update(
            mapOf(
                EventFields.ITEMS.value to newItems
            )
        ).await()
    }

    override suspend fun fetchItemsList(eventId: String): NetworkResponse<List<ItemModel>> =
        safeCall {
            return NetworkResponse.Success(getEventModel(eventId).items)
        }

    private suspend fun getEventModel(eventId: String): EventModel {
        val eventRef = getEventDocumentReference(eventId)
        return helper.mapDocumentToEventModel(eventRef.get().await())
    }

    private fun getEventDocumentReference(eventId: String): DocumentReference {
        return getEventsCollection()
            .document(eventId)
    }
}
