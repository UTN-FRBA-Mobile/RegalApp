package com.utn.frba.mobile.regalapp.itemList

import com.utn.frba.mobile.domain.models.ItemModel
import io.github.fededri.arch.interfaces.SideEffectInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

data class ItemsState(
    val title: String = "",
    val eventId: String = "",
    val items: List<ItemModel> = emptyList(),
    val filteredItems: List<ItemModel> = emptyList()
)

data class ItemsListRenderState(
    val title: String = "",
    val eventId: String = "",
    val items: List<ItemModel> = emptyList()
)

sealed class ItemsActions {
    data class SetInitialArguments(val eventId: String, val title: String) : ItemsActions()

    // region User initiated actions
    object FetchInitialList : ItemsActions()
    object OpenEventDetails : ItemsActions()
    data class OpenItemDetails(val item: ItemModel) : ItemsActions()
    object AddItemClicked : ItemsActions()
    object GoBack : ItemsActions()
    data class FilterItems(val query: String) : ItemsActions()
    // endregion

    //region processor actions
    data class HandleItemsList(val items: List<ItemModel>) : ItemsActions()

    object HandleError : ItemsActions()
    //endregion
}

sealed class ItemSideEffects(
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    override val coroutineScope: CoroutineScope? = null
) : SideEffectInterface {
    data class LoadItemsList(val eventId: String) : ItemSideEffects()
}

sealed class ListEvents {
    data class OpenEventDetails(val eventId: String) : ListEvents()
    data class OpenItemDetails(val item: ItemModel) : ListEvents()
    object OpenAddItemScreen : ListEvents()
    object BackButtonPressed : ListEvents()
}
