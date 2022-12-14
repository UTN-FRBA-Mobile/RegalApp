package com.utn.frba.mobile.regalapp.itemList

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.utn.frba.mobile.domain.models.ItemModel
import com.utn.frba.mobile.regalapp.R

@Composable
fun ItemCard(item: ItemModel, onClick: (ItemModel) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp),
    ) {
        Button(
            onClick = {
                onClick(item)
            },
            Modifier.weight(1F),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 5.dp, horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.weight(2F)
                ) {
                    item.name?.let { Text(text = it) }
                    if (item.status == true) {
                        Text(
                            text = stringResource(id = R.string.bought_by, item.boughtBy.orEmpty()),
                            fontSize = 10.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (item.status == true) {
                        Text(text = stringResource(id = R.string.bougth))
                    } else {
                        Text(text = stringResource(id = R.string.pending))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ItemCardPendingPreview() {
    val item = ItemModel(
        name = "Vasos",
    )
    ItemCard(item = item, onClick = {})
}

@Preview
@Composable
fun ItemCardCompletedPreview() {
    val item = ItemModel(
        name = "Vasos",
        status = true,
        boughtBy = "Gonzalo"
    )
    ItemCard(item = item, onClick = {})
}
