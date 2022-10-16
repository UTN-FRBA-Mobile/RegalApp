package com.utn.frba.mobile.regalapp.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.utn.frba.mobile.regalapp.R

@Composable
fun ItemCard(item: ItemModel) {
    Row(
        Modifier.fillMaxWidth().height(60.dp),
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Button(
            onClick = {},
            Modifier.weight(1F),
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Spacer(modifier = Modifier.width(5.dp))
                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.name)
                    if( item.status ) {
                        Text(
                            text = stringResource(id = R.string.bought_by, item.bought_by),
                            fontSize = 10.sp
                        )
                    }

                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(1F),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (item.status) {
                        Text(text = stringResource(id = R.string.bougth))
                    } else {
                        Text(text = stringResource(id = R.string.pending))
                    }
                }
                Spacer(modifier = Modifier.weight(1F))
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
    }
}
