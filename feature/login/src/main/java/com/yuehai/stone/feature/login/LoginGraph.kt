package com.yuehai.stone.feature.login

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yuehai.stone.feature.login.index.LoginRoute
import com.yuehai.stone.feature.login.recover.RecoverRoute

const val ROUTE_LOGIN = "route_login"
private const val ROUTE_LOGIN_INDEX = "route_login_index"
private const val ROUTE_LOGIN_RECOVER = "route_login_recover"
fun NavGraphBuilder.loginGraph(navController: NavController) {
    navigation(startDestination = ROUTE_LOGIN_INDEX, route = ROUTE_LOGIN) {
        composable(ROUTE_LOGIN_INDEX) {
            LoginRoute(viewModel()) { navController.navigate(ROUTE_LOGIN_RECOVER) }
        }
        composable(ROUTE_LOGIN_RECOVER) { RecoverRoute(viewModel(), navController::popBackStack) }
    }
}