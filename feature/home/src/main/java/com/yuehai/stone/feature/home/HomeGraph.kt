package com.yuehai.stone.feature.home

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yuehai.stone.feature.home.demo.DemoScreen
import com.yuehai.stone.feature.home.index.HomeScreen

const val ROUTE_HOME = "route_home"
private const val ROUTE_HOME_INDEX = "route_home_index"
private const val ROUTE_HOME_DEMO = "route_home_demo"
fun String.isHome(): Boolean = this == ROUTE_HOME_INDEX
fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = ROUTE_HOME_INDEX, route = ROUTE_HOME) {
        composable(ROUTE_HOME_INDEX) {
            HomeScreen(viewModel()) { navController.navigate(ROUTE_HOME_DEMO) }
        }
        composable(ROUTE_HOME_DEMO) { DemoScreen(viewModel(), navController::popBackStack) }
    }
}

