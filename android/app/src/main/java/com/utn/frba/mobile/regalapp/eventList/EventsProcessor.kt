package com.utn.frba.mobile.regalapp.eventList

import io.github.fededri.arch.interfaces.Processor
import javax.inject.Inject

class EventsProcessor @Inject constructor() : Processor<EventSideEffects, EventsActions> {
    override suspend fun dispatchSideEffect(effect: EventSideEffects): EventsActions {
        return when (effect) {
            is EventSideEffects.LoadEventsList -> loadEventsList(effect)
        }
    }

    private suspend fun loadEventsList(effect: EventSideEffects.LoadEventsList): EventsActions {
        // TODO
        return EventsActions.HandleEventsList(emptyList())
    }
}