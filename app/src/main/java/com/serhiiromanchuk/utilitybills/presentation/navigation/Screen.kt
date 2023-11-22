package com.serhiiromanchuk.utilitybills.presentation.navigation

sealed class Screen (
    val route: String
) {

    data object StartScreen : Screen(ROUTE_START_SCREEN)

    data object MainScreen : Screen(ROUTE_MAIN_SCREEN)

    data object InsertServiceScreen : Screen(ROUTE_INSERT_SERVICE_SCREEN)

    data object BillsArchiveScreen : Screen(ROUTE_BILLS_ARCHIVE_SCREEN)

    data object BillScreen : Screen(ROUTE_BILL_SCREEN)

    data object BillDetails : Screen(ROUTE_BILL_DETAILS)

    companion object {
        private const val ROUTE_START_SCREEN = "start_screen"
        private const val ROUTE_MAIN_SCREEN = "main_screen"
        private const val ROUTE_INSERT_SERVICE_SCREEN = "insert_service_screen"
        private const val ROUTE_BILLS_ARCHIVE_SCREEN = "bills_archive_screen"
        private const val ROUTE_BILL_SCREEN = "bill_screen"
        private const val ROUTE_BILL_DETAILS = "bill_details_screen"
    }
}