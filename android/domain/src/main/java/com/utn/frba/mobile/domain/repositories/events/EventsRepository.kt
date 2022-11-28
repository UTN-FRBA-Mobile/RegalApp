package com.utn.frba.mobile.domain.repositories.events

import com.utn.frba.mobile.domain.models.*

interface EventsRepository {
    suspend fun fetchUserEvents(): NetworkResponse<List<EventModel>>
    suspend fun fetchEvent(eventId: String): NetworkResponse<EventModel>
    suspend fun createEvent(data: Map<String, Any>): NetworkResponse<EventModel>
    suspend fun addItems(eventId: String, items: List<ItemModel>): NetworkResponse<EventModel>
    suspend fun editItem(eventId: String, itemId: String, model: ItemModel): NetworkResponse<EventModel>

    suspend fun fetchItemsList(eventId: String): NetworkResponse<List<ItemModel>>
    suspend fun fetchEventSettingsList(eventId: String): NetworkResponse<List<EventSettings>>

    // Event joining
    suspend fun joinEvent(eventId: String, enablePushNotifications: Boolean): NetworkResponse<String>

    /**
     * returns true if the user is already joined to the event
     */
    suspend fun isAlreadyJoined(eventId: String): NetworkResponse<Boolean>

}
