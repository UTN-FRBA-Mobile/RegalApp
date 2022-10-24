package com.utn.frba.mobile.regalapp.eventList

import com.utn.frba.mobile.domain.repositories.events.EventsRepository
import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class EventsProcessor @Inject constructor(
    private val eventsRepository: EventsRepository
) : Processor<EventSideEffects, EventsActions> {
    override suspend fun dispatchSideEffect(effect: EventSideEffects): EventsActions {
        return when (effect) {
            is EventSideEffects.LoadEventsList -> loadEventsList(effect)
        }
    }

    private suspend fun loadEventsList(effect: EventSideEffects.LoadEventsList): EventsActions {
        // TODO hardcoded user id
        val events = eventsRepository.fetchUserEvents("ECTF5lR1bPMUkiKBIELzBxirrn53")

        return EventsActions.HandleEventsList(events.data ?: emptyList())
    }
}