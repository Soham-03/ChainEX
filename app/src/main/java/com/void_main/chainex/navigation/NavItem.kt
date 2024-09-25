package com.example.fiintechapp.navigation

import com.void_main.chainex.R

sealed class NavItem(
    val title: String,
    val Icon: Int,
    val route:String
){
    object Home:NavItem("Home", R.drawable.house,"home")
    object Discover:NavItem("Discover",R.drawable.compass,"discover")
    object History:NavItem("History",R.drawable.history,"history")
    object Reward:NavItem("Reward",R.drawable.gift,"reward")
}
