package com.utn.frba.mobile.regalapp.eventList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.utn.frba.mobile.domain.models.EventModel
import com.utn.frba.mobile.regalapp.R

@Composable
fun EventList(events: List<EventModel>, contentPadding: PaddingValues, onEventClicked: (EventModel) -> Unit) {
    if (events.isNotEmpty()) {
        LazyColumn(
            contentPadding = contentPadding
        ) {
            items(events) { event ->
                Spacer(modifier = Modifier.height(Dp(20F)))
                EventItem(event = event, onEventClicked)
            }
        }
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize(1F)
                .padding(
                    horizontal = 40.dp
                )
        ) {
            Text(
                text = stringResource(id = R.string.empty_event_list),
                fontSize = 20.sp,
                color = Color.Gray
            )
        }
    }
}
