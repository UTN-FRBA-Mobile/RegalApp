package com.utn.frba.mobile.regalapp.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.R

@Composable
fun LoginScreen(viewModel: AuthenticationViewModel) {
    val state = viewModel.observeState()
        .collectAsState(initial = AuthenticationState()).value

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Email
        Row() {
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                label = {
                    Text(stringResource(R.string.email))
                },
                value = state.email.orEmpty(),
                onValueChange = {
                    viewModel.action(AuthenticationActions.SetEmail(it))
                }
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        // Password
        TextField(
            value = state.password.orEmpty(),
            label = {
                Text(stringResource(R.string.password))
            },
            modifier = Modifier.padding(top = 8.dp),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                viewModel.action(AuthenticationActions.SetPassword(it))
            }
        )

        Button(onClick = {
            viewModel.action(AuthenticationActions.LoginClicked)
        }) {
            Text(stringResource(R.string.login))
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }









        Row() {
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                label = {
                    Text(stringResource(R.string.reg_name))
                },
                value = state.reg_name.orEmpty(),
                onValueChange = {
                    viewModel.action(AuthenticationActions.SetRegName(it))
                }
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Row() {
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                label = {
                    Text(stringResource(R.string.reg_user))
                },
                value = state.reg_user.orEmpty(),
                onValueChange = {
                    viewModel.action(AuthenticationActions.SetRegUser(it))
                }
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        // Password
        TextField(
            value = state.reg_password.orEmpty(),
            label = {
                Text(stringResource(R.string.reg_password))
            },
            modifier = Modifier.padding(top = 8.dp),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                viewModel.action(AuthenticationActions.SetRegPassword(it))
            }
        )
        TextField(
            value = state.reg_password_again.orEmpty(),
            label = {
                Text(stringResource(R.string.reg_password_again))
            },
            modifier = Modifier.padding(top = 8.dp),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                viewModel.action(AuthenticationActions.SetRegPasswordAgain(it))
            }
        )

        Button(onClick = {
            //viewModel.action(AuthenticationActions.LoginClicked)
        }) {
            Text(stringResource(R.string.register))
        }
    }
}
