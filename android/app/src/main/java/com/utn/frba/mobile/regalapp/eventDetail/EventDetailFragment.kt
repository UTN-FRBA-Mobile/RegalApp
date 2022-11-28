package com.utn.frba.mobile.regalapp.eventDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.eventList.ListEvents
import com.utn.frba.mobile.regalapp.eventList.EventsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentKey(EventDetailFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class EventDetailFragment @Inject constructor(
    private val viewModelFactory: EventsViewModel.Factory
) : Fragment() {

    private val viewModel: EventsViewModel by navGraphViewModels(R.id.navigation_main) { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EditEventScreen(viewModel = viewModel)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEvents()
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            viewModel.observeEvents()
                .flowOn(Dispatchers.Main)
                .collect { event ->
                    when (event) {
                        is ListEvents.BackButtonPressed -> {
                            findNavController().popBackStack()
                        }
                        is ListEvents.MissingFields -> {
                            showMissingFieldsAlert()
                        }
                        is ListEvents.UpdateSuccess -> {
                            showUpdateSuccess()
                        }
                        is ListEvents.UpdateFailure -> {
                            showUpdateFailure()
                        }
                        else -> {}
                    }
                }
        }
    }

    private fun showMissingFieldsAlert() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.missing_fields_add_event))
            .setTitle(getString(R.string.required_fields))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showUpdateFailure() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.edit_event_failure_description))
            .setTitle(getString(R.string.error))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showUpdateSuccess() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.edit_event_success_description))
            .setTitle(getString(R.string.edit_event_success_title))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                findNavController().popBackStack()
            }
            .show()
    }
}
