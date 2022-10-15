package com.utn.frba.mobile.regalapp.eventList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.utn.frba.mobile.regalapp.R

@Composable
fun EventListScreen(navController: NavController) {
    val events = mutableListOf(
        EventModel("Mi cumple"),
        EventModel("Asado"),
        EventModel("Algo aburrido")
    )
    Scaffold(
        topBar = {
            TopBar()
        },
        content = { innerPadding ->
            EventList(events = events, contentPadding = innerPadding, navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(R.id.openAddEventFragment)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "new-item")
            }
        }
    )
}

@Composable
fun TopBar() {
    TopAppBar(title = {
        Image(
            painter = painterResource(id = R.drawable.user_icon_placeholder),
            contentDescription = stringResource(id = R.string.user_image_content_description),
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(id = R.string.events))
    })
}
