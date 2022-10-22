package com.utn.frba.mobile.regalapp.eventList

import io.github.fededri.arch.Next
import io.github.fededri.arch.interfaces.Updater
import javax.inject.Inject

typealias NextResult = Next<EventsState, EventSideEffects, ListEvents>

class EventsUpdater @Inject constructor() :
    Updater<EventsActions, EventsState, EventSideEffects, ListEvents> {
    override fun onNewAction(
        action: EventsActions,
        currentState: EventsState
    ): NextResult {
        return when (action) {
            is EventsActions.FetchInitialList -> fetchInitialList(currentState, action)
            is EventsActions.OpenEventDetails -> openEventDetails(currentState, action)
            is EventsActions.HandleEventsList -> Next.State(currentState.copy(events = action.events))
            is EventsActions.AddEventClicked -> Next.StateWithEvents(currentState, setOf(ListEvents.OpenAddEventScreen))
        }
    }

    private fun fetchInitialList(
        currentState: EventsState,
        action: EventsActions.FetchInitialList
    ): NextResult {
        // TODO
        return Next.State(currentState)
    }

    private fun openEventDetails(
        currentState: EventsState,
        action: EventsActions.OpenEventDetails
    ): NextResult {
        // fetch event details and emit navigation event
        return Next.StateWithEvents(
            currentState.copy(selectedEvent = action.event),
            events = setOf(ListEvents.OpenEventDetails(action.event))
        )
    }
}