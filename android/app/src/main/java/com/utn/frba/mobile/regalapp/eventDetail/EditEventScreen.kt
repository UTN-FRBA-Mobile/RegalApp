package com.utn.frba.mobile.regalapp.eventDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.eventList.EventsActions
import com.utn.frba.mobile.regalapp.eventList.EventsViewModel

@Composable
fun EditEventScreen(viewModel: EventsViewModel) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.edit),
                onCancelClick = {
                    viewModel.action(EventsActions.GoBack)
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(
                    horizontal = 20.dp
                )
            ) {
                EditEventForm(viewModel)
            }
        }
    )
}

@Composable
fun TopBar(
    title: String,
    onCancelClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(title)
        },
        navigationIcon = {
            IconButton(onClick = {
                onCancelClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.cancel)
                )
            }
        }
    )
}
