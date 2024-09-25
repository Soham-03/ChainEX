package com.example.fiintechapp.navigation


import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController:NavHostController){
    val navItems = listOf(NavItem.Home,NavItem.History,NavItem.Discover,NavItem.Reward)
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        painterResource(id =item.Icon ) ,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                        )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },

            )

        }
    }
}