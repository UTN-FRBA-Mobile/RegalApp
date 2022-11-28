package com.utn.frba.mobile.regalapp.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material.Surface
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.addEvent.AddEventViewModel
import com.utn.frba.mobile.regalapp.login.AuthenticationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@FragmentKey(ProfileFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class ProfileFragment @Inject constructor() : Fragment() {
    //val viewModel: ProfileViewModel by navGraphViewModels(R.id.navigation_main) { viewModelFactory }

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
                    //ProfileScreen(viewModel)
                    Text(text = "BASE DE PERFIL")
                }
            }
        }
    }
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val year: Int
        val month: Int
        val day: Int
        val calendar = Calendar.getInstance()

        // Fetching current year, month and day
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.time = Date()

        // Initialize state
        viewModel.action(AddEventActions.SetDate("$day/${month+1}/$year"))

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
    */
}
