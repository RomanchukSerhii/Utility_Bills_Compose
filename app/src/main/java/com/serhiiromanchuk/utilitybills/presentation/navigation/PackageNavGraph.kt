package com.serhiiromanchuk.utilitybills.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.serhiiromanchuk.utilitybills.presentation.navigation.PackageScreen.Companion.KEY_PACKAGE_ID
import com.serhiiromanchuk.utilitybills.presentation.navigation.PackageScreen.Companion.KEY_PACKAGE_NAME
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.add_package.AddPackageScreenRoot
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.choose_package.ChoosePackageScreenRoot
import com.serhiiromanchuk.utilitybills.presentation.screen.bill_packages.edit_package.EditPackageScreenRoot
import com.serhiiromanchuk.utilitybills.utils.NOT_FOUND_ID

fun NavGraphBuilder.packageNavGraph(
    navigationState: NavigationState
) {
    navigation(
        startDestination = PackageScreen.ChoosePackage.route,
        route = Graph.PACKAGE
    ) {

        composable(PackageScreen.ChoosePackage.route) {
            ChoosePackageScreenRoot(navigationState = navigationState)
        }

        composable(PackageScreen.AddPackage.route) {
            AddPackageScreenRoot(navigationState = navigationState)
        }

        composable(
            route = PackageScreen.EditPackage.route,
            arguments = listOf(
                navArgument(name = KEY_PACKAGE_NAME) {
                    type = NavType.StringType
                },
                navArgument(name = KEY_PACKAGE_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            val packageName = it.arguments?.getString(KEY_PACKAGE_NAME) ?: ""
            val packageId = it.arguments?.getLong(KEY_PACKAGE_ID) ?: NOT_FOUND_ID

            EditPackageScreenRoot(
                packageId = packageId,
                packageName = packageName,
                navigationState = navigationState
            )
        }
    }
}

sealed class PackageScreen(val route: String) {

    data object ChoosePackage : Screen(ROUTE_CHOOSE_PACKAGE)

    data object AddPackage : Screen(ROUTE_ADD_PACKAGE)

    data object EditPackage : Screen(ROUTE_EDIT_PACKAGE) {

        private const val ROUTE_FOR_ARGS = "edit_package_screen"

        fun getRoutWithArgs(packageName: String, billId: Long): String {
            return "$ROUTE_FOR_ARGS/$packageName/$billId"
        }
    }

    companion object {
        const val KEY_PACKAGE_ID = "package_id"
        const val KEY_PACKAGE_NAME = "package_name"

        private const val ROUTE_EDIT_PACKAGE =
            "edit_package_screen/{${KEY_PACKAGE_NAME}}/{${KEY_PACKAGE_ID}}"
        private const val ROUTE_ADD_PACKAGE = "add_package_screen"
        private const val ROUTE_CHOOSE_PACKAGE = "choose_package_screen"
    }
}