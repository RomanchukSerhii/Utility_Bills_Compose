package com.serhiiromanchuk.utilitybills.domain.mocks


import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

val fakeBillItem = Bill(
    address = "вул. Грушевського 23, кв. 235",
    payerName = "Романчук Сергій",
    month = "Січень",
    year = "2024",
    indexPosition = 0,
)

val fakeUtilityService = UtilityServiceItem(
    billCreatorId = 0,
    name = "Газ",
    tariff = 8.00,
    isMeterAvailable = true,
    previousValue = "8 965",
    currentValue = "25"
)

val fakeUtilityServicesList = listOf(
    UtilityServiceItem(
        id = 0,
        billCreatorId = 0,
        name = "Газ (доставка)",
        tariff = 57.00,
        isMeterAvailable = false
    ),
    UtilityServiceItem(
        id = 1,
        billCreatorId = 0,
        name = "Газ",
        tariff = 8.00,
        isMeterAvailable = true,
        previousValue = "8 965"
    ),
    UtilityServiceItem(
        id = 2,
        billCreatorId = 0,
        name = "Вода",
        tariff = 47.00,
        isMeterAvailable = true,
        previousValue = "952",
    ),
    UtilityServiceItem(
        id = 3,
        billCreatorId = 0,
        name = "Світло",
        tariff = 2.64,
        isMeterAvailable = true,
        previousValue = "883",
    ),
    UtilityServiceItem(
        id = 4,
        billCreatorId = 0,
        name = "ОСББ",
        tariff = 218.00,
        isMeterAvailable = false
    ),
)