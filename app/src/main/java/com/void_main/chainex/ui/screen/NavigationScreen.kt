package com.example.fiintechapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fiintechapp.navigation.NavItem

@Composable
fun NavigationScreen(modifier: Modifier,navController:NavHostController){

    NavHost(navController = navController, startDestination = NavItem.Home.route) {
        composable(NavItem.Home.route) { HomeScreen() }
        composable(NavItem.Discover.route) { DiscoverScreen() }
        composable(NavItem.History.route) { HistoryScreen() }
        composable(NavItem.Reward.route) { RewardScreen() }
    }
}