package com.utn.frba.mobile.regalapp.itemList

import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.domain.models.ItemModel
import io.github.fededri.arch.interfaces.SideEffectInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

data class ItemsState(
    val selectedEvent: EventModel,
    val selectedItem: ItemModel? = null,
)

// TODO Hardcode por ahora
fun defaultItemState(): ItemsState {
    val items = mutableListOf<ItemModel>(
        ItemModel("Vasos"),
        ItemModel("Bebidas", status = true, boughtBy = "Gonzalo"),
        ItemModel("Carne"),
        ItemModel("Ensalda"),
        ItemModel("Carbón", status = true, boughtBy = "Un nombre muy largooooo"),
    )
    val event = EventModel(
        id = "",
        name = "Asado",
        items = items,
        ownerId = "",
    )
    return ItemsState(event)
}

sealed class ItemsActions {
    // region User initiated actions
    object FetchInitialList : ItemsActions()
    data class OpenEventDetails(val event: EventModel) : ItemsActions()
    data class OpenItemDetails(val item: ItemModel): ItemsActions()
    object AddItemClicked : ItemsActions()
    object OpenEventsList : ItemsActions()
    // endregion

    //region processor actions
    data class HandleItemsList(val event: EventModel) : ItemsActions()
    //endregion


}

sealed class ItemSideEffects(
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    override val coroutineScope: CoroutineScope? = null
) : SideEffectInterface {
    object LoadItemList : ItemSideEffects()
}

sealed class ListEvents {
    data class OpenEventDetails(val event: EventModel) : ListEvents()
    data class OpenItemDetails(val item: ItemModel): ListEvents()
    object OpenAddItemScreen : ListEvents()
    object OpenEventsList : ListEvents()
}