package com.utn.frba.mobile.regalapp.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(viewModel: AuthenticationViewModel) {
    val state = viewModel.observeState().collectAsState(initial = AuthenticationState()).value

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        // reg_firstname
        TextField(value = state.reg_firstname.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetRegFirstname(it))
        })
        // reg_lastname
        TextField(value = state.reg_lastname.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetRegLastname(it))
        })
        // reg_username
        TextField(value = state.reg_username.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetRegUsername(it))
        })
        // reg_email
        TextField(value = state.reg_email.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetRegEmail(it))
        })
        // reg_password
        TextField(value = state.reg_password.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetRegPassword(it))
        })
        // reg_password_again
        TextField(value = state.reg_password_again.orEmpty(), onValueChange = {
            viewModel.action(AuthenticationActions.SetRegPasswordAgain(it))
        })


        Button(onClick = {

        }) {
            viewModel.action(AuthenticationActions.RegisterClicked)
        }
    }
}