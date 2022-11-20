package com.utn.frba.mobile.regalapp.addEvent

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.fededri.arch.ArchViewModel
import io.github.fededri.arch.ThreadInfo
import javax.inject.Inject

class AddEventViewModel(
    processor: AddEventProcessor,
    updater: AddEventUpdater,
    threadInfoProvider: ThreadInfo
) : ArchViewModel<AddEventActions, AddEventState, AddEventSideEffects, ListEvents, Nothing>(
    updater,
    initialState = AddEventState(),
    threadInfo = threadInfoProvider,
    processor = processor
) {

    class Factory @Inject constructor(
        private val processor: AddEventProcessor,
        private val updater: AddEventUpdater
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddEventViewModel(
                processor,
                updater,
                object : ThreadInfo {
                    override fun isMainThread(): Boolean {
                        return Looper.myLooper() == Looper.getMainLooper()
                    }
                }
            ) as T
        }
    }
}
