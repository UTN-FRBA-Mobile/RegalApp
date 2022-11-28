package com.utn.frba.mobile.regalapp.eventList

import io.github.fededri.arch.Next
import io.github.fededri.arch.interfaces.Updater
import timber.log.Timber
import javax.inject.Inject

typealias NextResult = Next<EventsState, EventSideEffects, ListEvents>

class EventsUpdater @Inject constructor() :
    Updater<EventsActions, EventsState, EventSideEffects, ListEvents> {
    override fun onNewAction(
        action: EventsActions,
        currentState: EventsState
    ): NextResult {
        Timber.i("Events list: Received action: $action")
        return when (action) {
            is EventsActions.FetchInitialList -> fetchInitialList(currentState, action)
            is EventsActions.OpenEventDetails -> openEventDetails(currentState, action)
            is EventsActions.OpenItemsList -> openItemList(currentState, action)
            is EventsActions.HandleEventsList -> handleEventsList(currentState, action)
            is EventsActions.AddEventClicked -> Next.StateWithEvents(
                currentState,
                setOf(ListEvents.OpenAddEventScreen)
            )
            is EventsActions.SetDeviceToken -> handleSetDeviceToken(currentState, action)
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
        action: EventsActions.OpenItemsList
    ): NextResult {
        // fetch event details and emit navigation event
        return Next.StateWithEvents(
            currentState.copy(selectedEvent = action.event),
            events = setOf(ListEvents.OpenItemsList(action.event))
        )
    }

    private fun handleSetDeviceToken(
        currentState: EventsState,
        action: EventsActions.SetDeviceToken
    ): NextResult {
        return Next.StateWithSideEffects(
            currentState,
            setOf(
                EventSideEffects.SetDeviceToken(action.token)
            )
        )
    }
}
