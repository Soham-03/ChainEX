package com.void_main.chainex.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.void_main.chainex.Utils
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun AiItenaryScreen(){
    var outputResponse by remember {
        mutableStateOf("")
    }
    var coordinates by remember {
        mutableStateOf(ArrayList<LatLng>())
    }
    val model = GenerativeModel(
        modelName = "gemini-1.5-flash-001",
        apiKey = "AIzaSyBBRAZznn1_CA1B4g4L7dXZLeaujwjpei0",
    )

    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ){
        val scope = rememberCoroutineScope()
        var start by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var days by remember {
            mutableStateOf(TextFieldValue(""))
        }
        Spacer(Modifier.height(40.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(
                value = start,
                onValueChange = {
                    start = it
                },
                placeholder = {
                    Text("Where are you?")
                }
            )
            Spacer(Modifier.height(12.dp))
            TextField(
                value = days,
                onValueChange = {
                    days = it
                },
                placeholder = {
                    Text("For how many days?")
                }
            )

            Spacer(Modifier.height(12.dp))

            Button(onClick = {
                scope.launch {
                    val input = "Recommend the itenary, I'm starting from ${start} and i have ${days} days to travel here, I'm travelling this month of january, don't ask any other questions. just start recommending" +
                            "Morning:\n" +
                            "Activities\n" +
                            "Recommended breakfast spots\n" +
                            "Afternoon:\n" +
                            "Activities\n" +
                            "Recommended lunch spots\n" +
                            "Evening:\n" +
                            "Activities\n" +
                            "Recommended dinner spots\n" +
                            "Additionally, at the end of each day, provide an array of latitude and longitude coordinates for all the locations mentioned in that day's itinerary\n" +
                            "\n" +
                            "Example Output:\n" +
                            "\n" +
                            "Day 1:\n" +
                            "\n" +
                            "Morning:\n" +
                            "Check-in to hotel in South Mumbai (Colaba, Fort, or Nariman Point)\n" +
                            "Breakfast at Leopold Cafe (18.9272° N, 72.8342° E)\n" +
                            "Afternoon:\n" +
                            "Visit Gateway of India (18.9221° N, 72.8346° E)\n" +
                            "Explore Colaba Causeway (18.9221° N, 72.8346° E)\n" +
                            "Evening:\n" +
                            "Dinner at Marine Drive (18.9272° N, 72.8342° E)\n" +
                            "Stroll along Marine Drive\n" +
                            "Day 1 Coordinates:"
//                model.generateContentStream(input)
//                    .collect { response ->
//                        outputResponse += response.text
//                    }
                    val response = model.generateContent(input)
                    outputResponse = response.text.toString()
                    coordinates = response.text?.let { extractCoordinates(it) }!!
                    println(coordinates)

                }
            }){
                Text(text = "Generate")
            }

        }
        Spacer(Modifier.height(18.dp))
        if(coordinates.isNotEmpty()){
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(coordinates[0], 10f)
            }
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(18.dp)),
                cameraPositionState = cameraPositionState
            ) {
                val context = LocalContext.current
                for (mark in coordinates) {
                    MarkerInfoWindow(
                        state = MarkerState(position = mark),
                        onInfoWindowClick = {
                            Utils.clickedLatLng = mark
                            val intent = Intent(context, StreetViewActivity::class.java)
                            context.startActivity(intent)
                        },
//                icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_cargo_select)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .border(
                                    BorderStroke(1.dp, Color.Black),
                                    RoundedCornerShape(10)
                                )
                                .clip(RoundedCornerShape(10))
                                .background(Color.Black)
                                .padding(8.dp)
                        ) {
                            Text("Click to view", fontWeight = FontWeight.Bold, color = Color.White)
//                            Text("GGWP", fontWeight = FontWeight.Medium, color = Color.White)
                        }
                    }
                }
            }
        }

        Text(text = outputResponse)
//        println(outputResponse)
    }
}

fun extractCoordinates(text: String): ArrayList<LatLng> {
    // Create ArrayList to store coordinates
    val coordinates = ArrayList<LatLng>()

    // Regular expression to match coordinates
    val pattern = """(\d+\.\d+)°\s*N,\s*(\d+\.\d+)°\s*E""".toRegex()

    // Find all matches in the text
    pattern.findAll(text).forEach { matchResult ->
        val (latitude, longitude) = matchResult.destructured
        coordinates.add(
            LatLng(
                latitude.toDouble(),
                longitude.toDouble()
            )
        )
    }

    return coordinates
}