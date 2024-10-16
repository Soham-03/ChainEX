package com.void_main.chainex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.fiintechapp.ui.screen.HomeScreen
import com.example.fiintechapp.ui.screen.MainScreen
import com.example.fiintechapp.ui.screen.RewardScreen
import com.void_main.chainex.ui.theme.ChainEXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            ChangeSystemBarsTheme(!isSystemInDarkTheme())
            ChainEXTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
//                    val navController = rememberNavController()
//                    MainScreen(navController)
////                    DiscoverScreen()
                    RewardScreen()
                }
            }
        }
    }

//    @Composable
//    private fun ChangeSystemBarsTheme(lightTheme: Boolean) {
//        val barColor = MaterialTheme.colorScheme.background.toArgb()
//        LaunchedEffect(lightTheme) {
//            if (lightTheme) {
//                enableEdgeToEdge(
//                    statusBarStyle = SystemBarStyle.light(
//                        barColor, barColor,
//                    ),
//                    navigationBarStyle = SystemBarStyle.light(
//                        barColor, barColor,
//                    ),
//                )
//            } else {
//                enableEdgeToEdge(
//                    statusBarStyle = SystemBarStyle.dark(
//                        barColor,
//                    ),
//                    navigationBarStyle = SystemBarStyle.dark(
//                        barColor,
//                    ),
//                )
//            }
//        }
//    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChainEXTheme {
        Greeting("Android")
    }
}