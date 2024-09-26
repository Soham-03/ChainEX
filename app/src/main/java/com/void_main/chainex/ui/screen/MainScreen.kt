package com.example.fiintechapp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fiintechapp.navigation.BottomNavigationBar

@Composable
fun MainScreen(navController: NavHostController){

    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomNavigationBar(navController = navController)
            }
        }
    ) {
        NavigationScreen(Modifier.padding(it.calculateTopPadding()),navController)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainScreen(){

}


