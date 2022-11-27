package com.utn.frba.mobile.regalapp.joinEvent

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.fededri.arch.ArchViewModel
import io.github.fededri.arch.ThreadInfo
import javax.inject.Inject

class JoinEventViewModel(
    processor: JoinEventProcessor,
    updater: JoinEventUpdater,
    threadInfoProvider: ThreadInfo
) : ArchViewModel<JoinEventActions, JoinEventState, JoinEventSideEffects, JoinEventEvents, JoinEventState>(
    updater = updater,
    processor = processor,
    threadInfo = threadInfoProvider,
    initialState = JoinEventState.Loading
) {

    class Factory @Inject constructor(
        val processor: JoinEventProcessor,
        val updater: JoinEventUpdater
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return JoinEventViewModel(
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