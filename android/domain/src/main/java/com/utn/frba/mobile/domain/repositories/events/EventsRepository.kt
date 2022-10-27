package com.utn.frba.mobile.domain.repositories.events

import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.domain.models.NetworkResponse

interface EventsRepository {
    suspend fun fetchUserEvents(userId: String): NetworkResponse<List<EventModel>>
}