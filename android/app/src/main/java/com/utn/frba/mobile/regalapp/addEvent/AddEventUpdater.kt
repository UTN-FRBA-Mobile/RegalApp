package com.utn.frba.mobile.regalapp.addEvent

import com.utn.frba.mobile.domain.models.EventModel
import io.github.fededri.arch.Next
import timber.log.Timber
import javax.inject.Inject

typealias NextResult = Next<AddEventState, AddEventSideEffects, ListEvents>

class AddEventUpdater @Inject constructor() :
    io.github.fededri.arch.interfaces.Updater<AddEventActions, AddEventState, AddEventSideEffects, ListEvents> {
    override fun onNewAction(
        action: AddEventActions,
        currentState: AddEventState
    ): NextResult {
        Timber.i("Add event: received action $action")
        return when (action) {
            is AddEventActions.SetName -> Next.State(currentState.copy(name = action.name))
            is AddEventActions.SetDate -> Next.State(currentState.copy(date = action.date))
            is AddEventActions.SetImage -> Next.State(currentState.copy(image = action.image))
            is AddEventActions.SaveEventClicked -> handleSave(currentState)
            is AddEventActions.HandleCreateEventSuccess -> handleCreateEventSuccess(currentState, action)
            is AddEventActions.CancelClicked -> handleCancel(currentState)
        }
    }

    private fun handleSave(currentState: AddEventState): NextResult {
        if (
            currentState.name.isNullOrBlank() ||
            currentState.date.isNullOrBlank() ||
            currentState.image.isNullOrBlank()
        ) {
            return Next.State(currentState)
        }
        // TODO: handle real images
        val newEvent = EventModel(
            name = currentState.name,
            date = currentState.date,
            image = "mock",
            ownerId = "user"
        )
        return Next.StateWithSideEffects(
            currentState,
            setOf(
                AddEventSideEffects.SaveEvent(event = newEvent)
            )
        )
    }

    private fun handleCancel(currentState: AddEventState): NextResult {
        return Next.StateWithEvents(currentState, setOf(ListEvents.CancelCreate))
    }

    private fun handleCreateEventSuccess(
        currentState: AddEventState,
        action: AddEventActions
    ): NextResult {
        // TODO: Revisar action sin usar
        return Next.StateWithEvents(
            currentState,
            setOf(ListEvents.CancelCreate)
        )
    }
}
