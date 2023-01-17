package com.yuehai.stone.feature.profile

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yuehai.stone.feature.profile.index.ProfileRoute
import com.yuehai.stone.feature.profile.setting.SettingScreen

const val ROUTE_PROFILE = "route_profile"
private const val ROUTE_PROFILE_INDEX = "route_profile_index"
private const val ROUTE_PROFILE_SETTING = "route_profile_setting"
fun String.isProfile(): Boolean = this == ROUTE_PROFILE_INDEX
fun NavGraphBuilder.profileGraph(navController: NavController, checkUpgrade: () -> Unit) {
    navigation(startDestination = ROUTE_PROFILE_INDEX, route = ROUTE_PROFILE) {
        composable(ROUTE_PROFILE_INDEX) {
            ProfileRoute(viewModel()) { navController.navigate(ROUTE_PROFILE_SETTING) }
        }
        composable(ROUTE_PROFILE_SETTING) {
            SettingScreen(viewModel(),checkUpgrade, navController::popBackStack)
        }
    }
}


