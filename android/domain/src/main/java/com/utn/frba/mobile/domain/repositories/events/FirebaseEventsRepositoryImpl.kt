package com.utn.frba.mobile.domain.repositories.events

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.anvil.annotations.ContributesBinding
import com.utn.frba.mobile.domain.DBCollections
import com.utn.frba.mobile.domain.dataStore.UserDataStore
import com.utn.frba.mobile.domain.di.AppScope
import com.utn.frba.mobile.domain.models.*
import com.utn.frba.mobile.domain.models.EventFields
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
            val userId = userDataStore.getLoggedUser().id

            // Get events owned by the user
            val ownsQueryResult = getEventsCollection()
                .whereEqualTo(EventFields.OWNER_ID.value, userId)
                .get()
                .await()

            val ownedEvents = ownsQueryResult.documents.mapNotNull {
                helper.mapDocumentToEventModel(it)
            }

            // Get events where the user was invited
            val isParticipantQueryResult = getEventsCollection()
                .whereArrayContains(EventFields.PARTICIPANTS.value, userId)
                .get()
                .await()

            val joinedEvents = isParticipantQueryResult.documents.mapNotNull { helper.mapDocumentToEventModel(it) }

            // Make sure we don't repeat events using a set
            val allEvents = mutableSetOf<EventModel>()
            allEvents.addAll(ownedEvents)
            allEvents.addAll(joinedEvents)
            return NetworkResponse.Success(allEvents.toList())
        }

    override suspend fun joinEvent(eventId: String, enablePushNotifications: Boolean): NetworkResponse<String>  = safeCall {
        val userId = userDataStore.getLoggedUser().id
        val eventRef = getEventsCollection().document(eventId)
        val currentParticipants = getEventModel(eventId).participants
        val newParticipants = currentParticipants + userId
        eventRef.update(EventFields.PARTICIPANTS.value, newParticipants)

        // set the flag for receiving push notifications
        db.collection(DBCollections.EVENT_SETTINGS.value)
            .document(eventId)
            .set(
                EventSettings(
                    eventId,
                    userId,
                    enablePushNotifications
                )
            )

        val eventName = getEventModel(eventId).name
        NetworkResponse.Success(eventName)
    }

    override suspend fun fetchEvent(eventId: String): NetworkResponse<EventModel>  = safeCall {
        val eventModel = getEventModel(eventId)
        NetworkResponse.Success(eventModel)
    }

    private fun getEventsCollection(): CollectionReference {
        return db.collection(DBCollections.EVENTS.value)
    }

    private fun getSettingsCollection(): CollectionReference {
        return db.collection(DBCollections.EVENT_SETTINGS.value)
    }

    override suspend fun fetchEventSettingsList(eventId: String): NetworkResponse<List<EventSettings>> = safeCall {
        val settings = getSettingsCollection().whereEqualTo(
            "eventId",
            eventId
        ).get().await().documents.mapNotNull { helper.mapDocumentToEventSettings(it) }
        return NetworkResponse.Success(settings)
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

    override suspend fun editItem(
        eventId: String,
        itemId: String,
        model: ItemModel
    ): NetworkResponse<EventModel> = safeCall {

        val currentModel = getEventModel(eventId)
        val newItems = currentModel.items.map {
            if (it.id == model.id) {
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
        NetworkResponse.Success(currentModel.copy(
            items = newItems
        ))

    }

    override suspend fun fetchItemsList(eventId: String): NetworkResponse<List<ItemModel>> =
        safeCall {
            return NetworkResponse.Success(getEventModel(eventId).items)
        }

    override suspend fun isAlreadyJoined(eventId: String): NetworkResponse<Boolean> = safeCall {
        val userId = userDataStore.getLoggedUser().id

        val eventsSnapshot = db.collection(DBCollections.EVENTS.value)
            .whereArrayContains(EventFields.PARTICIPANTS.value, userId)
            .get()
            .await()

        val events = eventsSnapshot.documents.map { helper.mapDocumentToEventModel(it) }
        val isAlreadyJoined = events.find {
            it.id == eventId
        } != null
        NetworkResponse.Success(isAlreadyJoined)
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
