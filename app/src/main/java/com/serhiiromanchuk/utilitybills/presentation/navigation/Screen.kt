package com.serhiiromanchuk.utilitybills.presentation.navigation

sealed class Screen (
    val route: String
) {

    data object StartScreen : Screen(ROUTE_START_SCREEN)

    data object HomeScreen : Screen(ROUTE_HOME_SCREEN)

    data object InsertServiceScreen : Screen(ROUTE_INSERT_SERVICE_SCREEN) {

        private const val ROUTE_FOR_ARGS = "insert_service_screen"

        fun getRoutWithArgs(utilityServiceId: Long, billCreatorId: Long): String {
            return "$ROUTE_FOR_ARGS/$utilityServiceId/$billCreatorId"
        }
    }

    data object BillsArchiveScreen : Screen(ROUTE_BILLS_ARCHIVE_SCREEN)

    data object BillScreen : Screen(ROUTE_BILL_SCREEN)

    data object BillDetails : Screen(ROUTE_BILL_DETAILS)

    companion object {
        const val KEY_SERVICE_ID = "utility_service_id"
        const val KEY_BILL_CREATOR_ID = "bill_creator_id"

        private const val ROUTE_START_SCREEN = "start_screen"
        private const val ROUTE_HOME_SCREEN = "home_screen"
        private const val ROUTE_INSERT_SERVICE_SCREEN = "insert_service_screen/{$KEY_SERVICE_ID}/{$KEY_BILL_CREATOR_ID}"
        private const val ROUTE_BILLS_ARCHIVE_SCREEN = "bills_archive_screen"
        private const val ROUTE_BILL_SCREEN = "bill_screen"
        private const val ROUTE_BILL_DETAILS = "bill_details_screen"
    }
}