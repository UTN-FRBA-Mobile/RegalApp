package com.utn.frba.mobile.regalapp.addItem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.utn.frba.mobile.regalapp.R
import timber.log.Timber

@Composable
fun AddItemForm(viewModel: AddItemViewModel) {
    val state = viewModel.observeState().collectAsState(initial = AddItemState()).value
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(1F)
    ) {
        OutlinedTextField(
            value = state.name.orEmpty(),
            onValueChange = {
                viewModel.action(AddItemActions.SetName(it))
            },
            label = {
                Text(stringResource(id = R.string.name))
            },
            modifier = Modifier.fillMaxWidth(1F),
        )
        OutlinedTextField(
            value = state.quantity.orEmpty(),
            onValueChange = {
                viewModel.action(AddItemActions.SetQuantity(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = {
                Text(stringResource(id = R.string.quantity))
            },
            modifier = Modifier.fillMaxWidth(1F),
        )
        OutlinedTextField(
            value = state.price.toString(),
            onValueChange = { value ->
                try {
                    val price = value.toFloat()
                    viewModel.action(AddItemActions.SetPrice(price))
                } catch (e: Exception) {
                    Timber.e(e)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            label = {
                Text(stringResource(id = R.string.price))
            },
            modifier = Modifier.fillMaxWidth(1F),
        )
        OutlinedTextField(
            value = state.location.orEmpty(),
            onValueChange = {
                viewModel.action(AddItemActions.SetLocation(it))
            },
            label = {
                Text(stringResource(id = R.string.location))
            },
            modifier = Modifier.fillMaxWidth(1F),
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
