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
            // Item list
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

            // Item detail
            is ItemsActions.CloseItemDetail -> Next.StateWithEvents(
                currentState,
                setOf(ListEvents.CloseDetailPressed)
            )
            is ItemsActions.OpenEditItem -> Next.StateWithEvents(
                currentState.copy(
                    editingItem = currentState.selectedItem
                ),
                setOf(ListEvents.OpenEditItemScreen)
            )
            is ItemsActions.ChangeItemStatus -> handleChangeItemStatus(currentState, action)

            // Edit item
            is ItemsActions.SetName -> handleSetName(currentState, action)
            is ItemsActions.SetQuantity -> handleSetQuantity(currentState, action)
            is ItemsActions.SetPrice -> handleSetPrice(currentState, action)
            is ItemsActions.SetLocation -> handleSetLocation(currentState, action)
            is ItemsActions.UpdateItemClicked -> handleUpdate(currentState, action)
            is ItemsActions.HandleUpdateSucceeded -> handleUpdateSucceeded(currentState, action)
            is ItemsActions.CloseEditItem -> handleCloseEditItem(currentState, action)
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

    private fun handleChangeItemStatus(
        currentState: ItemsState,
        action: ItemsActions.ChangeItemStatus,
    ): NextResult {
        return Next.StateWithSideEffects(
            currentState,
            setOf(
                ItemSideEffects.ChangeItemStatus(
                    eventId = currentState.eventId,
                    itemId = currentState.selectedItem?.id ?: "",
                    action.item,
                )
            )
        )

    }

    private fun handleSetName(
        currentState: ItemsState,
        action: ItemsActions.SetName
    ): NextResult  {
        require(currentState.editingItem != null) {
            "Editing item not set"
        }
        return Next.State(
            currentState.copy(
                editingItem = currentState.editingItem.copy(
                    name = action.name
                )
            )
        )
    }

    private fun handleSetQuantity(
        currentState: ItemsState,
        action: ItemsActions.SetQuantity
    ): NextResult  {
        require(currentState.editingItem != null) {
            "Editing item not set"
        }
        return Next.State(
            currentState.copy(
                editingItem = currentState.editingItem.copy(
                    quantity = action.quantity.toLong()
                )
            )
        )
    }


    private fun handleSetPrice(
        currentState: ItemsState,
        action: ItemsActions.SetPrice
    ): NextResult {
        require(currentState.editingItem != null) {
            "Editing item not set"
        }
        return Next.State(
            currentState.copy(
                editingItem = currentState.editingItem.copy(
                    price = action.price
                )
            )
        )
    }

    private fun handleSetLocation(
        currentState: ItemsState,
        action: ItemsActions.SetLocation
    ): NextResult  {
        require(currentState.editingItem != null) {
            "Editing item not set"
        }
        return Next.State(
            currentState.copy(
                editingItem = currentState.editingItem.copy(
                    location = action.location
                )
            )
        )
    }

    private fun handleUpdate(
        currentState: ItemsState,
        action: ItemsActions.UpdateItemClicked,
    ): NextResult {
        val eventId = currentState.eventId
        require(eventId != null) {
            "Event id not set"
        }
        require(currentState.editingItem != null) {
            "Editing item not set"
        }
        return Next.StateWithSideEffects(
            currentState.copy(
                isLoading = true
            ),
            setOf(
                ItemSideEffects.UpdateItem(
                    eventId = eventId,
                    itemId = currentState.editingItem.id,
                    item = currentState.editingItem
                )
            )
        )
    }

    private fun handleUpdateSucceeded(
        currentState: ItemsState,
        action: ItemsActions.HandleUpdateSucceeded,
    ): NextResult {

        return Next.StateWithEvents(
            currentState.copy(
                isLoading = false,
                selectedItem = action.item,
                items = action.itemList,
            ),
            setOf(
                ListEvents.CloseDetailPressed
            )
        )
    }


    private fun handleCloseEditItem(
        currentState: ItemsState,
        action: ItemsActions.CloseEditItem
    ): NextResult {
        return Next.StateWithEvents(
            currentState.copy(
                isLoading = false,
                selectedItem = action.item ?: currentState.selectedItem,
            ),
            setOf(ListEvents.CloseDetailPressed)
        )
    }
}
