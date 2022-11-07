package com.utn.frba.mobile.regalapp.addItem

import com.utn.frba.mobile.domain.models.ItemModel
import io.github.fededri.arch.interfaces.SideEffectInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

data class AddItemState(
    val eventId: String? = null,
    val name: String? = null,
    val quantity: String? = null,
    val price: Float? = null,
    val location: String? = null,
)

sealed class AddItemActions {
    // region User initiated actions
    data class SetEventId(val eventId: String) : AddItemActions()
    object FetchInitialList : AddItemActions()
    data class SetName(val name: String) : AddItemActions()
    data class SetQuantity(val quantity: String) : AddItemActions()
    data class SetPrice(val price: Float) : AddItemActions()
    data class SetLocation(val location: String) : AddItemActions()
    object SaveItemClicked : AddItemActions()
    data class HandleSaveItemSucceded(val item: ItemModel) : AddItemActions()
    object CancelClicked : AddItemActions()
    // endregion
}

sealed class AddItemSideEffects(
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    override val coroutineScope: CoroutineScope? = null
) : SideEffectInterface {
    data class SaveItem(val item: ItemModel, val eventId: String) : AddItemSideEffects()
}
sealed class ListEvents {
    object DismissScreen : ListEvents()
}
