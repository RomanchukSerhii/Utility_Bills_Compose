package com.serhiiromanchuk.utilitybills.utils.mocks


import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.model.UtilityService

val fakeBillItem = Bill(
    packageCreatorId = 0L,
    address = "вул. Грушевського 23, кв. 235",
    payerName = "Романчук Сергій",
    date = "Лютий 2024"
)

val fakeUtilityService = UtilityService(
    billCreatorId = 0,
    name = "Газ",
    tariff = 8.00,
    isMeterAvailable = true,
    previousValue = "8 965",
    currentValue = "25"
)

val fakeUtilityServicesList = listOf(
    UtilityService(
        id = 0,
        billCreatorId = 0,
        name = "Газ (доставка)",
        tariff = 57.00,
        isMeterAvailable = false
    ),
    UtilityService(
        id = 1,
        billCreatorId = 0,
        name = "Газ",
        tariff = 8.00,
        isMeterAvailable = true,
        previousValue = "8 965"
    ),
    UtilityService(
        id = 2,
        billCreatorId = 0,
        name = "Вода",
        tariff = 47.00,
        isMeterAvailable = true,
        previousValue = "952",
    ),
    UtilityService(
        id = 3,
        billCreatorId = 0,
        name = "Світло",
        tariff = 2.64,
        isMeterAvailable = true,
        previousValue = "883",
    ),
    UtilityService(
        id = 4,
        billCreatorId = 0,
        name = "ОСББ",
        tariff = 218.00,
        isMeterAvailable = false
    ),
)