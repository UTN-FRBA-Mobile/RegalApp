package com.utn.frba.mobile.regalapp.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material.Surface
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.squareup.anvil.annotations.ContributesMultibinding
import com.utn.frba.mobile.domain.di.ActivityScope
import com.utn.frba.mobile.domain.di.FragmentKey
import com.utn.frba.mobile.domain.repositories.auth.UserRepository
import com.utn.frba.mobile.regalapp.login.LoginActivity
import com.utn.frba.mobile.regalapp.login.ProfileScreen
import com.utn.frba.mobile.regalapp.map.ProfileActions
import com.utn.frba.mobile.regalapp.map.ProfileViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentKey(ProfileFragment::class)
@ContributesMultibinding(ActivityScope::class, Fragment::class)
class ProfileFragment @Inject constructor(
    private val viewModelFactory: ProfileViewModel.Factory,
    private val userRepository: UserRepository
) : Fragment() {
    val viewModel: ProfileViewModel by viewModels<ProfileViewModel> { viewModelFactory }

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
                    Column() {
                        ProfileScreen(viewModel) {
                            lifecycleScope.launch {
                                userRepository.logout()
                                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                            }
                        }
                    }
                    //Text(text = "BASE DE PERFIL")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.action(ProfileActions.FetchUser)
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
