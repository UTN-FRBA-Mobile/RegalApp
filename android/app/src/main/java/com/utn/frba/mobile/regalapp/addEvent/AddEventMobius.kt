package com.utn.frba.mobile.regalapp.addEvent

import com.utn.frba.mobile.domain.models.AddEventModel
import io.github.fededri.arch.interfaces.SideEffectInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

data class AddEventState(
    val name: String? = null,
    val date: String? = null,
    val image: String? = null
)

sealed class AddEventActions {
    // region User initiated actions
    data class SetName(val name: String) : AddEventActions()
    data class SetDate(val date: String) : AddEventActions()
    data class SetImage(val image: String) : AddEventActions()
    object CreateEventClicked : AddEventActions()
    object  HandleCreateEventSuccess : AddEventActions()
    object HandleCreateEventFailure : AddEventActions()
    object CancelClicked : AddEventActions()
    // endregion
}

sealed class AddEventSideEffects(
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    override val coroutineScope: CoroutineScope? = null
) : SideEffectInterface {
    data class CreateEvent(val event: AddEventModel) : AddEventSideEffects()
}

sealed class ListEvents {
    object CancelCreate : ListEvents()
    object MissingFields: ListEvents()
    object CreationSuccess: ListEvents()
    object CreationFailure: ListEvents()
}
