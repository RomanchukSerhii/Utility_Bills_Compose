package com.serhiiromanchuk.utilitybills.presentation.navigation

sealed class Screen (
    val route: String
) {

    data object StartScreen : Screen(ROUTE_START_SCREEN)









    data object BillsArchiveScreen : Screen(ROUTE_BILLS_ARCHIVE_SCREEN)

    data object BillScreen : Screen(ROUTE_BILL_SCREEN)

    data object BillDescriptionScreen : Screen(ROUTE_BILL_DESCRIPTION_SCREEN) {

        private const val ROUTE_FOR_ARGS = "bill_description_screen"

        fun getRoutWithArgs(billId: Long): String {
            return "$ROUTE_FOR_ARGS/$billId"
        }
    }

    data object BillDetailsScreen : Screen(ROUTE_BILL_DETAILS) {

        private const val ROUTE_FOR_ARGS = "bill_details_screen"

        fun getRoutWithArgs(billId: Long): String {
            return "$ROUTE_FOR_ARGS/$billId"
        }
    }

    companion object {


        private const val ROUTE_START_SCREEN = "start_screen"



        private const val ROUTE_BILL_SCREEN = "bill_screen"
        private const val ROUTE_BILL_DESCRIPTION_SCREEN = "bill_description_screen/{$KEY_BILL_ID}"
        private const val ROUTE_BILL_DETAILS = "bill_details_screen/{$KEY_BILL_ID}"



        private const val ROUTE_BILLS_ARCHIVE_SCREEN = "bills_archive_screen"
    }
}