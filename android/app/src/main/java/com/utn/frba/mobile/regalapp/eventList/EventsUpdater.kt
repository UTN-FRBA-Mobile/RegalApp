package com.utn.frba.mobile.regalapp.eventList

import com.utn.frba.mobile.domain.models.EditEventModel
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
            is EventsActions.SetName -> handleSetName(currentState, action)
            is EventsActions.SetDate -> handleSetDate(currentState, action)
            is EventsActions.SetImage -> handleSetImage(currentState, action)
            is EventsActions.UpdateEventClicked -> handleUpdate(currentState)
            is EventsActions.HandleUpdateSucceeded -> handleUpdateSuccess(currentState)
            is EventsActions.HandleUpdateFailure -> handleUpdateFailure(currentState)
            is EventsActions.GoBack -> handleGoBack(currentState)
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
        action.event
        return Next.StateWithEvents(
            currentState.copy(selectedEvent = action.event),
            events = setOf(ListEvents.OpenItemsList(action.event))
        )
    }

    private fun handleSetName(
        currentState: EventsState,
        action: EventsActions.SetName
    ): NextResult {
        require(currentState.selectedEvent != null) {
            "Event to edit not set"
        }
        return Next.State(
            currentState.copy(
                selectedEvent = currentState.selectedEvent.copy(
                    name = action.name
                )
            )
        )
    }

    private fun handleSetDate(
        currentState: EventsState,
        action: EventsActions.SetDate
    ): NextResult {
        require(currentState.selectedEvent != null) {
            "Event to edit not set"
        }
        return Next.State(
            currentState.copy(
                selectedEvent = currentState.selectedEvent.copy(
                    date = action.date
                )
            )
        )
    }

    private fun handleSetImage(
        currentState: EventsState,
        action: EventsActions.SetImage
    ): NextResult {
        require(currentState.selectedEvent != null) {
            "Event to edit not set"
        }
        return Next.State(
            currentState.copy(
                selectedEvent = currentState.selectedEvent.copy(
                    // image = action.image
                )
            )
        )
    }

    private fun handleUpdate(currentState: EventsState): NextResult {
        // TODO: handle images
        if(currentState.selectedEvent !== null) {
            val (id, name, _, date) = currentState.selectedEvent
            return if (
                name.isNullOrBlank() || date.isNullOrBlank()
            ) {
                Next.StateWithEvents(currentState, setOf(ListEvents.MissingFields))
            } else {
                val modifiedEvent = EditEventModel(
                    eventId = id,
                    name = name,
                    date = date
                )
                Next.StateWithSideEffects(
                    currentState,
                    setOf(
                        EventSideEffects.UpdateEvent(event = modifiedEvent)
                    )
                )
            }
        }
        return Next.StateWithEvents(currentState, setOf(ListEvents.MissingFields))
    }

    private fun handleUpdateSuccess(currentState: EventsState): NextResult {
        return Next.StateWithEvents(currentState, setOf(ListEvents.UpdateSuccess))
    }

    private fun handleUpdateFailure(currentState: EventsState): NextResult {
        return Next.StateWithEvents(currentState, setOf(ListEvents.UpdateFailure))
    }

    private fun handleGoBack(currentState: EventsState): NextResult {
        require(currentState.selectedEvent != null) {
            "Event not set"
        }
        return Next.StateWithEvents(
            EventsState(selectedEvent = currentState.selectedEvent),
            setOf(ListEvents.BackButtonPressed)
        )
    }
}
