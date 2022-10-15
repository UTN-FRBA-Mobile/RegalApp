package com.utn.frba.mobile.regalapp.eventList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController

@Composable
fun EventList(events: List<EventModel>, contentPadding: PaddingValues, navigator: NavController) {
    LazyColumn(
        contentPadding = contentPadding
    ) {
        items(events) { event ->
            Spacer(modifier = Modifier.height(Dp(20F)))
            EventItem(event = event, navigator)
        }
    }
}
