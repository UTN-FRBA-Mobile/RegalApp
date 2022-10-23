package com.utn.frba.mobile.regalapp.items

import io.github.fededri.arch.Next
import io.github.fededri.arch.interfaces.Updater
import javax.inject.Inject

typealias NextResult = Next<ItemsState, ItemSideEffects, ListEvents>

class ItemsUpdater @Inject constructor() :
    Updater<ItemsActions, ItemsState, ItemSideEffects, ListEvents> {
    override fun onNewAction(
        action: ItemsActions,
        currentState: ItemsState
    ): NextResult {
        return when (action) {
            is ItemsActions.FetchInitialList -> fetchInitialList(currentState, action)
            is ItemsActions.OpenEventDetails -> openEventDetails(currentState, action)
            is ItemsActions.OpenItemDetails -> openItemDetails(currentState, action)
            is ItemsActions.HandleItemsList -> Next.State(currentState.copy(selectedEvent = action.event))
            is ItemsActions.AddItemClicked -> Next.StateWithEvents(currentState, setOf(ListEvents.OpenAddItemScreen))
        }
    }

    private fun fetchInitialList(
        currentState: ItemsState,
        action: ItemsActions.FetchInitialList
    ): NextResult {
        // TODO
        return Next.State(currentState)
    }

    private fun openEventDetails(
        currentState: ItemsState,
        action: ItemsActions.OpenEventDetails
    ): NextResult {
        // fetch event details and emit navigation event
        return Next.StateWithEvents(
            currentState.copy(selectedEvent = action.event),
            events = setOf(ListEvents.OpenEventDetails(action.event))
        )
    }

    private fun openItemDetails(
        currentState: ItemsState,
        action: ItemsActions.OpenItemDetails
    ): NextResult {
        // fetch event details and emit navigation event
        return Next.StateWithEvents(
            currentState.copy(selectedItem = action.item),
            events = setOf(ListEvents.OpenItemDetails(action.item))
        )
    }

}