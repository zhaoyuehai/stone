package com.yuehai.stone.app.full

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yuehai.stone.core.ui.theme.AndrooidTheme
import com.yuehai.stone.feature.home.ROUTE_HOME
import com.yuehai.stone.feature.home.homeGraph
import com.yuehai.stone.feature.home.isHome
import com.yuehai.stone.feature.login.ROUTE_LOGIN
import com.yuehai.stone.feature.login.loginGraph
import com.yuehai.stone.feature.profile.ROUTE_PROFILE
import com.yuehai.stone.feature.profile.isProfile
import com.yuehai.stone.feature.profile.profileGraph

@Composable
fun Root() {
    AndrooidTheme {
        val viewModel: AppViewModel = viewModel()
        val navController = rememberNavController()
        when (viewModel.pageType) {
            AppViewModel.PageType.Splash -> {}
            AppViewModel.PageType.Login -> Login(navController)
            AppViewModel.PageType.Main -> Main(navController, viewModel::checkUpgrade)
        }
//        viewModel.versionData?.let { UpgradeDialog(it, viewModel::dismissUpgradeDialog) }
    }
}

@Composable
private fun Login(navController: NavHostController) {
    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_LOGIN,
            modifier = Modifier.padding(paddingValues)
        ) { loginGraph(navController) }
    }
}


@Composable
private fun Main(navController: NavHostController, checkUpgrade: () -> Unit) {
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route ?: ROUTE_HOME
    Scaffold(bottomBar = {
        MainBottomNavigation(currentRoute) {
            navController.navigate(it) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_HOME,
            modifier = Modifier.padding(paddingValues)
        ) {
            homeGraph(navController)
            profileGraph(navController, checkUpgrade)
        }
    }
}

@Composable
private fun MainBottomNavigation(currentRoute: String, onSelected: (String) -> Unit) {
    BottomNavigation {
        BottomNavigationItem(selected = currentRoute.isHome(),
            onClick = { onSelected(ROUTE_HOME) },
            label = { Text(stringResource(R.string.home)) },
            icon = {
                Icon(
                    painter = painterResource(if (currentRoute.isHome()) R.drawable.ic_upcoming_border else R.drawable.ic_upcoming),
                    contentDescription = null
                )
            })
        BottomNavigationItem(selected = currentRoute.isProfile(),
            onClick = { onSelected(ROUTE_PROFILE) },
            label = { Text(stringResource(R.string.profile)) },
            icon = {
                Icon(
                    painter = painterResource(if (currentRoute.isProfile()) R.drawable.ic_menu_book_border else R.drawable.ic_menu_book),
                    contentDescription = null
                )
            })
    }
}