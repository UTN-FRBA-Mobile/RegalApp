package com.utn.frba.mobile.regalapp.itemList

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
            is ItemsActions.HandleItemsList -> Next.State(currentState.copy(items = action.items, filteredItems = action.items))
            is ItemsActions.AddItemClicked -> Next.StateWithEvents(
                currentState,
                setOf(ListEvents.OpenAddItemScreen)
            )
            is ItemsActions.GoBack -> Next.StateWithEvents(
                currentState,
                setOf(ListEvents.BackButtonPressed)
            )
            is ItemsActions.SetInitialArguments -> handleSetInitialArgs(currentState, action)
            is ItemsActions.HandleError -> Next.State(currentState) // TODO handle error
            is ItemsActions.FilterItems -> handleFilterItems(currentState, action)
            is ItemsActions.CloseItemDetail -> Next.StateWithEvents(
                currentState,
                setOf(ListEvents.CloseDetailPressed)
            )
        }
    }

    private fun handleFilterItems(
        currentState: ItemsState,
        action: ItemsActions.FilterItems
    ): NextResult {
        return Next.State(
            currentState.copy(
                filteredItems = currentState.items.filter {
                    it.name?.contains(
                        action.query
                    ) == true
                }
            )
        )
    }

    private fun handleSetInitialArgs(
        currentState: ItemsState,
        action: ItemsActions.SetInitialArguments
    ): NextResult {
        return Next.StateWithSideEffects(
            currentState.copy(eventId = action.eventId, title = action.title),
            setOf(
                ItemSideEffects.LoadItemsList(action.eventId)
            )
        )
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
            currentState,
            events = setOf(ListEvents.OpenEventDetails(currentState.eventId))
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
