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
            is EventsActions.OpenItemList -> openItemList(currentState, action)
            is EventsActions.HandleEventsList -> handleEventsList(currentState, action)
            is EventsActions.AddEventClicked -> Next.StateWithEvents(
                currentState,
                setOf(ListEvents.OpenAddEventScreen)
            )
        }
    }

    private fun fetchInitialList(
        currentState: EventsState,
        action: EventsActions.FetchInitialList
    ): NextResult {
        return Next.StateWithSideEffects(
            currentState.copy(loading = true),
            sideEffects = setOf(EventSideEffects.LoadEventsList)
        )
    }

    private fun handleEventsList(
        currentState: EventsState,
        action: EventsActions.HandleEventsList
    ) =
        Next.State(currentState.copy(loading = false, events = action.events))

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

    private fun openItemList(
        currentState: EventsState,
        action: EventsActions.OpenItemList
    ): NextResult {
        // fetch event details and emit navigation event
        return Next.StateWithEvents(
            currentState.copy(selectedEvent = action.event),
            events = setOf(ListEvents.OpenItemList(action.event))
        )
    }

}