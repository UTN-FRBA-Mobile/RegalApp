package com.utn.frba.mobile.regalapp.addEvent

import com.utn.frba.mobile.domain.models.AddEventModel
import io.github.fededri.arch.Next
import io.github.fededri.arch.interfaces.Updater
import timber.log.Timber
import javax.inject.Inject

typealias NextResult = Next<AddEventState, AddEventSideEffects, ListEvents>

class AddEventUpdater @Inject constructor() :
    Updater<AddEventActions, AddEventState, AddEventSideEffects, ListEvents> {
    override fun onNewAction(
        action: AddEventActions,
        currentState: AddEventState
    ): NextResult {
        Timber.i("Add event: received action $action")
        return when (action) {
            is AddEventActions.SetName -> Next.State(currentState.copy(name = action.name))
            is AddEventActions.SetDate -> Next.State(currentState.copy(date = action.date))
            is AddEventActions.SetImage -> Next.State(currentState.copy(image = action.image))
            is AddEventActions.CreateEventClicked -> handleCreate(currentState)
            is AddEventActions.CancelClicked -> handleCancel(currentState)
            is AddEventActions.HandleCreateEventSuccess -> handleCreationSuccess(currentState)
            is AddEventActions.HandleCreateEventFailure -> handleCreationFailure(currentState)
        }
    }

    private fun handleCreate(currentState: AddEventState): NextResult {
        // TODO: handle images
        return if (
            currentState.name.isNullOrBlank() ||
            currentState.date.isNullOrBlank()
        ) {
            Next.StateWithEvents(currentState, setOf(ListEvents.MissingFields))
        } else {
        val newEvent = AddEventModel(
            name = currentState.name,
            date = currentState.date
        )
        Next.StateWithSideEffects(
            currentState,
            setOf(
                AddEventSideEffects.CreateEvent(event = newEvent)
            )
        )
        }
    }

    private fun handleCancel(currentState: AddEventState): NextResult {
        return Next.StateWithEvents(currentState, setOf(ListEvents.CancelCreate))
    }

    private fun handleCreationSuccess(currentState: AddEventState): NextResult {
        return Next.StateWithEvents(currentState, setOf(ListEvents.CreationSuccess))
    }

    private fun handleCreationFailure(currentState: AddEventState): NextResult {
        return Next.StateWithEvents(currentState, setOf(ListEvents.CreationFailure))
    }
}
