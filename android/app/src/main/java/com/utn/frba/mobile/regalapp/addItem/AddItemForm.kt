package com.utn.frba.mobile.regalapp.addItem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.domain.models.ItemModel
import com.utn.frba.mobile.regalapp.R
import com.utn.frba.mobile.regalapp.map.LocationField
import timber.log.Timber

val SPACING = 5.dp
@Composable
fun ItemForm(
    item: ItemModel,
    onNameChange: (String) -> Unit? = {},
    onQuantityChange: (String) -> Unit = {},
    onPriceChange: (String) -> Unit = {},
    onLocationChange: (String) -> Unit = {},
    onCoordinatesChange: (lat: Double, lng: Double) -> Unit = {_,_ ->},
    readOnly: Boolean = false
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(1F)
    ) {
        TextField(
            value = item.name.orEmpty(),
            onValueChange = {
                onNameChange(it)
            },
            label = {
                Text(stringResource(id = R.string.name))
            },
            modifier = Modifier
                .fillMaxWidth(1F),
            readOnly = readOnly,
        )
        Spacer(modifier = Modifier.height(SPACING))
        TextField(
            value = item.quantity?.toString().orEmpty(),
            onValueChange = {
                onQuantityChange(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = {
                Text(stringResource(id = R.string.quantity))
            },
            modifier = Modifier.fillMaxWidth(1F),
            readOnly = readOnly,
        )
        Spacer(modifier = Modifier.height(SPACING))
        TextField(
            value = item.price?.toString().orEmpty(),
            onValueChange = {
                onPriceChange(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            label = {
                Text(stringResource(id = R.string.price))
            },
            modifier = Modifier.fillMaxWidth(1F),
            readOnly = readOnly,
        )
        Spacer(modifier = Modifier.height(SPACING))
        LocationField(
            location = item.location.orEmpty(),
            latitude = item.latitude,
            longitude = item.longitude,
            onLocationChange = onLocationChange,
            onCoordinatesChange = onCoordinatesChange,
            readOnly = readOnly,
        )
    }
}
@Composable
fun AddItemForm(viewModel: AddItemViewModel) {
    val state = viewModel.observeState().collectAsState(initial = AddItemState()).value
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(1F)
    ) {
        ItemForm(
            item = ItemModel(
                name = state.name.orEmpty(),
                quantity = state.quantity.orEmpty().toLongOrNull(),
                price = state.price,
                location = state.location.orEmpty()
            ),
            onNameChange = {
                viewModel.action(AddItemActions.SetName(it))
            },
            onQuantityChange = {
                viewModel.action(AddItemActions.SetQuantity(it))
            },
            onPriceChange = { value ->
                try {
                    val price = value.toDouble()
                    viewModel.action(AddItemActions.SetPrice(price))
                } catch (e: Exception) {
                    Timber.e(e)
                }
            },
            onLocationChange = {
                viewModel.action(AddItemActions.SetLocation(it))
            }
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Button(
            onClick = {
                viewModel.action(AddItemActions.SaveItemClicked)
            },
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.add))
        }
    }
}
