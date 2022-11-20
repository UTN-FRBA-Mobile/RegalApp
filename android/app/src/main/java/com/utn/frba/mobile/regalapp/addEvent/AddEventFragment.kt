package com.utn.frba.mobile.regalapp.addEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material.Surface
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@FragmentKey(AddEventFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class AddEventFragment @Inject constructor(
    private val viewModelFactory: AddEventViewModel.Factory
) : Fragment() {
    val viewModel: AddEventViewModel by navGraphViewModels(R.id.navigation_main) { viewModelFactory }

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
                    AddEventScreen(viewModel)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mYear: Int
        val mMonth: Int
        val mDay: Int
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        // Initialize state
        viewModel.action(AddEventActions.SetDate("$mDay/${mMonth+1}/$mYear"))

        observeEvents()
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            viewModel.observeEvents()
                .flowOn(Dispatchers.Main)
                .collect { event ->
                    when (event) {
                        is ListEvents.CancelCreate -> {
                            findNavController().popBackStack()
                        }
                        is ListEvents.MissingFields -> {
                            showMissingFieldsAlert()
                        }
                        is ListEvents.CreationFailure -> {
                            showCreationFailure()
                        }
                        is ListEvents.CreationSuccess -> {
                            showCreationSuccessAlert()
                        }
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

    private fun showCreationFailure() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.add_event_failure_description))
            .setTitle(getString(R.string.error))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showCreationSuccessAlert() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.add_event_success_description))
            .setTitle(getString(R.string.add_event_success_title))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                findNavController().popBackStack()
            }
            .show()
    }
}
