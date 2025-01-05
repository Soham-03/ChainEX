package com.void_main.chainex.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.StreetViewPanoramaOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.streetview.StreetView
import com.google.maps.android.ktx.MapsExperimentalFeature
import com.void_main.chainex.Utils
import com.void_main.chainex.ui.screen.ui.theme.ChainEXTheme

class StreetViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChainEXTheme {
                StreetViewScreen()
            }
        }
    }
}

@OptIn(MapsExperimentalFeature::class)
@Composable
fun StreetViewScreen(){
    StreetView(
        streetViewPanoramaOptionsFactory = {
            StreetViewPanoramaOptions().position(Utils.clickedLatLng)
        },
        isPanningGesturesEnabled = true,
        isStreetNamesEnabled = true,
        isUserNavigationEnabled = true,
        isZoomGesturesEnabled = true
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    ChainEXTheme {
//        Greeting4("Android")
    }
}