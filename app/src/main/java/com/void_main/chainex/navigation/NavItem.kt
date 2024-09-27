package com.example.fiintechapp.navigation

import com.void_main.chainex.R

sealed class NavItem(
    val title: String,
    val Icon: Int,
    val route:String,
    val selectedICon: Int
){
    object Home:NavItem("Home", R.drawable.ic_home,"home", selectedICon = R.drawable.ic_home_selected)
    object Discover:NavItem("Discover",R.drawable.compass,"discover", selectedICon = R.drawable.ic_home_selected)
    object History:NavItem("History",R.drawable.history,"history", selectedICon = R.drawable.ic_home_selected)
    object Reward:NavItem("Reward",R.drawable.gift,"reward", selectedICon = R.drawable.ic_home_selected)
}
