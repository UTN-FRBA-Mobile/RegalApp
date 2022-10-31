package com.utn.frba.mobile.regalapp.addItem

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.fededri.arch.ArchViewModel
import io.github.fededri.arch.ThreadInfo
import javax.inject.Inject

class AddItemViewModel(
    processor: AddItemProcessor,
    updater: AddItemUpdater,
    threadInfoProvider: ThreadInfo
) : ArchViewModel<AddItemActions, AddItemState, AddItemSideEffects, ListEvents, Nothing>(
    updater,
    initialState = AddItemState(),
    threadInfo = threadInfoProvider,
    processor = processor
) {

    class Factory @Inject constructor(
        private val processor: AddItemProcessor,
        private val updater: AddItemUpdater
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddItemViewModel(
                processor,
                updater,
                object: ThreadInfo {
                    override fun isMainThread(): Boolean {
                        return Looper.myLooper() == Looper.getMainLooper()
                    }
                }
            ) as T
        }
    }
}