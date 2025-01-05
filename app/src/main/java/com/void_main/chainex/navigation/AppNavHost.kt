//package com.void_main.chainex.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.example.fiintechapp.ui.screen.MainScreen
//import com.void_main.chainex.ui.screen.PaymentScreen
//import com.void_main.chainex.ui.screen.RechargeScreen
//
//enum class Screen {
//                  Home,
//    AddFunds,
//    Payments
//}
//sealed class NavigationItem(val route: String) {
//    object Home: NavigationItem(Screen.Home.name)
//    object AddFunds : NavigationItem(Screen.AddFunds.name)
//    object Payment : NavigationItem(Screen.Payments.name)
//}
//
//@Composable
//fun AppNavHost(
//    modifier: Modifier = Modifier,
//    navController: NavHostController,
//    startDestination: String = NavigationItem.Home.route
//) {
//    NavHost(
//        modifier = modifier,
//        navController = navController,
//        startDestination = startDestination
//    ) {
//        composable(NavigationItem.AddFunds.route) {
//            RechargeScreen(navController)
//        }
//        composable(NavigationItem.Payment.route) {
//            PaymentScreen(navController)
//        }
//        composable(NavigationItem.Home.route) {
//            MainScreen(navController)
//        }
//    }
//}