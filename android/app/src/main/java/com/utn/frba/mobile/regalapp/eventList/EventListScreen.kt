package com.utn.frba.mobile.regalapp.eventList

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.login.AuthenticationActions

@Composable
fun EventListScreen(viewModel: EventsViewModel) {

    val state = viewModel.observeState().collectAsState(initial = EventsState())

    Scaffold(
        topBar = {
            TopBar(viewModel)
        },
        content = { innerPadding ->
            EventList(events = state.value.events, contentPadding = innerPadding) {
                viewModel.action(EventsActions.OpenItemsList(it))
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.action(EventsActions.AddEventClicked) // 1
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "new-item")
            }
        }
    )
}

@Composable
private fun TopBar(viewModel: EventsViewModel) {
    val state = viewModel.observeState().collectAsState(initial = EventsState())
    TopAppBar(title = {
        Image(
            painter = painterResource(id = R.drawable.user_icon_placeholder),
            contentDescription = stringResource(id = R.string.user_image_content_description),
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable {
                    viewModel.action(EventsActions.ProfileClicked)
                }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(id = R.string.events))
    })
}
