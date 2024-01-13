package com.serhiiromanchuk.utilitybills.presentation.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.UtilityBillsTheme
import com.serhiiromanchuk.utilitybills.presentation.core.components.PrimaryButton

@Composable
fun MainScreenLayout(
    modifier: Modifier = Modifier,
    mainScreenUiState: MainScreenUiState,
    onDeleteButtonClick: () -> Unit,
    onEditClick: () -> Unit,
    onCheckIconClick: (Boolean) -> Unit,
    previousValueChange: (Int) -> Unit,
    currentValueChange: (Int) -> Unit
) {
    Column {
        val addressListTest = listOf("Грушевського 23, кв.235", "Грушевського 23, кв.171")

//        HomeScreenHeader(
//            billItem = BillItem(
//                address = addressListTest[0],
//                month = month,
//                year = 2023,
//                cardNumber = "4444 2514 2684 1354"
//            ),
//            addressList = addressListTest,
//            onCardNumberEditClick = { /*TODO*/ },
//            changeBillAddress = {}
//        ) {
//
//        }
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Row {
                var isChecked by rememberSaveable { mutableStateOf(true) }
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = !isChecked
                        onCheckIconClick(isChecked)
                    }
                )
//                UtilityServiceDetails(
//                    modifier = Modifier
//                        .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
//                        .weight(1f),
//                    utilityService = utilityServiceTest,
//                    previousValueChange = previousValueChange,
//                    currentValueChange = currentValueChange
//                )
            }
        }

        PrimaryButton(text = "Видалити", onClick = onDeleteButtonClick )
    }

}


@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun UtilityServiceLayoutPreview() {
    UtilityBillsTheme(darkTheme = false) {
        Column {
            MainScreenLayout(
                mainScreenUiState = MainScreenUiState.Initial,
                onDeleteButtonClick = {},
                onEditClick = { /*TODO*/ },
                onCheckIconClick = { /*TODO*/ },
                previousValueChange = {},
                currentValueChange = {}
            )
        }

    }
}