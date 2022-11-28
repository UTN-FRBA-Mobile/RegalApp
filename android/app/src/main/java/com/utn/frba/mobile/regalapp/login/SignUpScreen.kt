package com.utn.frba.mobile.regalapp.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.R

@Composable
fun SignUpScreen(
    viewModel: AuthenticationViewModel
) {
    val state = viewModel.observeState().collectAsState(initial = AuthenticationState()).value

    Scaffold(
        topBar = {
            SignUpAppBar {
                viewModel.action(AuthenticationActions.GoBack)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = state.email.orEmpty(),
                label = {
                    Text(stringResource(R.string.email))
                },
                onValueChange = {
                    viewModel.action(AuthenticationActions.SetEmail(it))
                })

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

            TextField(
                value = state.repeatedPassword.orEmpty(),
                label = {
                    Text(stringResource(R.string.repeat_password))
                },
                modifier = Modifier.padding(top = 8.dp),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    viewModel.action(AuthenticationActions.SetRepeatedPassword(it))
                }
            )

            TextField(
                value = state.name.orEmpty(),
                modifier = Modifier.padding(top = 8.dp),
                label = {
                    Text(stringResource(R.string.name))
                },
                onValueChange = {
                    viewModel.action(AuthenticationActions.SetName(it))
                })

            TextField(
                value = state.lastName.orEmpty(),
                modifier = Modifier.padding(top = 8.dp),
                label = {
                    Text(stringResource(R.string.lastname))
                },
                onValueChange = {
                    viewModel.action(AuthenticationActions.SetLastName(it))
                })

            Button(onClick = {
                viewModel.action(AuthenticationActions.RegisterAccount)
            }) {
                Text(stringResource(R.string.register_account))
            }
        }
    }
}

@Composable
private fun SignUpAppBar(
    onBackClick: () -> Unit
) {
    TopAppBar(title = {
        Text(stringResource(R.string.register_new_account))
    }, navigationIcon = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(onClick = {
                onBackClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
    })
}