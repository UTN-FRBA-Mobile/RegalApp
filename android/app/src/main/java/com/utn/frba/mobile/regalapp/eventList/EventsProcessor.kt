package com.utn.frba.mobile.regalapp.eventList

import com.utn.frba.mobile.domain.models.NetworkResponse
import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class EventsProcessor @Inject constructor(
    private val eventsRepository: EventsRepository
) : Processor<EventSideEffects, EventsActions> {
    override suspend fun dispatchSideEffect(effect: EventSideEffects): EventsActions {
        return when (effect) {
            is EventSideEffects.LoadEventsList -> loadEventsList()
            is EventSideEffects.UpdateEvent -> updateEvent(effect)
        }
    }

    private suspend fun loadEventsList(): EventsActions {
        val events = eventsRepository.fetchUserEvents()
        return EventsActions.HandleEventsList(events.data ?: emptyList())
    }


    private suspend fun updateEvent(effect: EventSideEffects.UpdateEvent): EventsActions {
        val eventMap = mapOf(
            "name" to effect.event.name,
            "date" to effect.event.date
        )
        return when (eventsRepository.editEvent(effect.event.eventId, eventMap as Map<String, Any>)) {
            is NetworkResponse.Success -> {
                EventsActions.HandleUpdateSucceeded(effect.event)
            }
            is NetworkResponse.Error -> {
                EventsActions.HandleUpdateFailure(effect.event)
            }
        }
    }
}
