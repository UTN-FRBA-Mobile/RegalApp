package com.utn.frba.mobile.regalapp.eventList

import com.utn.frba.mobile.domain.models.EditEventModel
import com.utn.frba.mobile.domain.models.EventModel
import io.github.fededri.arch.interfaces.SideEffectInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

data class EventsState(
    val events: List<EventModel> = emptyList(),
    val selectedEvent: EventModel? = null,
    val loading: Boolean = false
)

sealed class EventsActions {
    // region User initiated actions
    object FetchInitialList : EventsActions()
    data class OpenEventDetails(val event: EventModel) : EventsActions()
    data class OpenItemsList(val event: EventModel) : EventsActions()
    object AddEventClicked: EventsActions()
    object GoBack: EventsActions()
    object ProfileClicked: EventsActions()
    // endregion

    // event edit actions
    data class SetName(val name: String) : EventsActions()
    data class SetDate(val date: String) : EventsActions()
    data class SetImage(val image: String) : EventsActions()
    data class UpdateEventClicked(val event: EventModel) : EventsActions()
    data class HandleUpdateSucceeded(val event: EditEventModel): EventsActions()
    data class HandleUpdateFailure(val event: EditEventModel): EventsActions()

    //region processor actions
    data class HandleEventsList(val events: List<EventModel>) : EventsActions()
    data class SetDeviceToken(val token: String): EventsActions()
    //endregion
}

sealed class EventSideEffects(
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    override val coroutineScope: CoroutineScope? = null
) : SideEffectInterface {
    object LoadEventsList : EventSideEffects()
    data class UpdateEvent(val event: EditEventModel) : EventSideEffects()
    data class SetDeviceToken(val token: String): EventSideEffects()
}


sealed class ListEvents {
    data class OpenEventDetails(val event: EventModel) : ListEvents()
    data class OpenItemsList(val event: EventModel) : ListEvents()
    object OpenAddEventScreen: ListEvents()
    object BackButtonPressed : ListEvents()
    object MissingFields: ListEvents()
    object UpdateSuccess: ListEvents()
    object UpdateFailure: ListEvents()
    object OpenProfileScreen: ListEvents()
}
