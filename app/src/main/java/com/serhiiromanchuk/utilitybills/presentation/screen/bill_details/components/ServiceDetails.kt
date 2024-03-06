package com.serhiiromanchuk.utilitybills.presentation.screen.bill_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.utilitybills.R
import com.serhiiromanchuk.utilitybills.domain.model.UtilityService
import com.serhiiromanchuk.utilitybills.presentation.core.components.TitleTextOnSurface
import com.serhiiromanchuk.utilitybills.utils.addCurrencySign

@Composable
fun ServiceDetails(
    service: UtilityService
) {
    Column {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))
        ServiceText(title = "Назва послуги: ", value = service.name)
        if (service.isMeterAvailable) {
            ServiceText(title = "Тариф: ", value = service.tariff.toString().addCurrencySign())
            ServiceText(
                title = "Поточні показники: ",
                value = "${service.currentValue} ${service.unitOfMeasurement.title}"
            )
            ServiceText(
                title = "Попередні показники: ",
                value = "${service.previousValue} ${service.unitOfMeasurement.title}"
            )
            ServiceText(title = "Спожито: ", value = service.consumed)
        }
        ServiceText(
            title = "Сума: ",
            value = String.format(stringResource(R.string.service_total_value), service.total)
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ServiceText(
    title: String,
    value: String,
) {
    Column {
        Row {
            TitleTextOnSurface(
                text = title,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TitleTextOnSurface(text = value)
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_medium)))
    }
}