package com.serhiiromanchuk.utilitybills.domain.mocks


import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

val fakeBillItem = BillItem(
    address = "вул. Грушевського 23, кв. 235",
    month = "Січень",
    year = "2024",
    cardNumber = "1354 4978 3577 4648"
)

val fakeUtilityService = UtilityServiceItem(
    name = "Газ",
    tariff = 8.00,
    isMeterAvailable = true,
    previousValue = 8965,
    currentValue = 25
)

val fakeUtilityServicesList = listOf(
    UtilityServiceItem(
        name = "Газ (доставка)",
        tariff = 57.00,
        isMeterAvailable = false
    ),
    UtilityServiceItem(
        name = "Газ",
        tariff = 8.00,
        isMeterAvailable = true,
        previousValue = 8965
    ),
    UtilityServiceItem(
        name = "Вода",
        tariff = 47.00,
        isMeterAvailable = true,
        previousValue = 952,
    ),
    UtilityServiceItem(
        name = "Світло",
        tariff = 2.64,
        isMeterAvailable = true,
        previousValue = 883,
    ),
    UtilityServiceItem(
        name = "ОСББ",
        tariff = 218.00,
        isMeterAvailable = false
    ),
)