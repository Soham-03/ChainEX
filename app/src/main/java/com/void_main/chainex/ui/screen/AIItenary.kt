package com.void_main.chainex.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.void_main.chainex.Utils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun AiItineraryScreen(
    onBackClick: () -> Unit = {}
) {
    // Define colors
    val lightBackground = Color(0xFFF8F9FA)
    val accentGreen = Color(0xFF4CAF50)
    val buttonBlue = Color(0xFF1A73E8)
    val cardBg = Color(0xFFFFFFFF)
    val textColor = Color(0xFF202124)
    val secondaryTextColor = Color(0xFF5F6368)
    val borderGray = Color(0xFFE0E0E0)

    var outputResponse by remember { mutableStateOf("") }
    var coordinates by remember { mutableStateOf(ArrayList<LatLng>()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val model = GenerativeModel(
        modelName = "gemini-1.5-flash-001",
        apiKey = "AIzaSyBBRAZznn1_CA1B4g4L7dXZLeaujwjpei0",
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = " Travel Planner",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightBackground)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            val scope = rememberCoroutineScope()
            var location by remember { mutableStateOf(TextFieldValue("")) }
            var days by remember { mutableStateOf(TextFieldValue("")) }

            // Header Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "Plan Your Perfect Trip",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Let us create a personalized itinerary for you",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Input Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Location Input
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Current Location") },
                        placeholder = { Text("Where are you?") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = accentGreen
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentGreen,
                            unfocusedBorderColor = borderGray,
                            focusedLabelColor = accentGreen,
                            unfocusedLabelColor = secondaryTextColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Days Input
                    OutlinedTextField(
                        value = days,
                        onValueChange = { days = it },
                        label = { Text("Duration") },
                        placeholder = { Text("For how many days?") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = accentGreen
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentGreen,
                            unfocusedBorderColor = borderGray,
                            focusedLabelColor = accentGreen,
                            unfocusedLabelColor = secondaryTextColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Generate Button
                    Button(
                        onClick = {
                            scope.launch {
                                if (location.text.isNotEmpty() && days.text.isNotEmpty()) {
                                    isLoading = true
                                    errorMessage = ""
                                    outputResponse = ""
                                    coordinates.clear()

                                    try {
                                        val input = "Recommend the itinerary, I'm starting from ${location.text} and I have ${days.text} days to travel here, I'm travelling this month of January, don't ask any other questions. just start recommending" +
                                                "\nMorning:\n" +
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

                                        val response = model.generateContent(input)
                                        outputResponse = response.text.toString()
                                        coordinates = response.text?.let { extractCoordinates(it) }!!
                                    } catch (e: Exception) {
                                        errorMessage = "Failed to generate itinerary. Please try again."
                                    } finally {
                                        isLoading = false
                                    }
                                } else {
                                    errorMessage = "Please fill in all fields"
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = buttonBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generating...", fontSize = 16.sp)
                        } else {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate Itinerary", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Error Message
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            // Map Section
            AnimatedVisibility(
                visible = coordinates.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Map,
                                contentDescription = null,
                                tint = accentGreen,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Itinerary Map",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }

                        val cameraPositionState = rememberCameraPositionState {
                            position = CameraPosition.fromLatLngZoom(coordinates[0], 12f)
                        }

                        GoogleMap(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(12.dp)),
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
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .border(
                                                BorderStroke(1.dp, Color.White),
                                                RoundedCornerShape(10)
                                            )
                                            .clip(RoundedCornerShape(10))
                                            .background(accentGreen)
                                            .padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            "View in Street View",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Itinerary Output
            AnimatedVisibility(
                visible = outputResponse.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null,
                                tint = accentGreen,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Your Personalized Itinerary",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }

                        // Format the output response
                        val formattedResponse = formatItineraryText(outputResponse)
                        formattedResponse.forEach { section ->
                            when (section.type) {
                                SectionType.DAY -> {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        colors = CardDefaults.cardColors(containerColor = accentGreen.copy(alpha = 0.1f)),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = section.text,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = accentGreen,
                                            modifier = Modifier.padding(12.dp)
                                        )
                                    }
                                }
                                SectionType.TIME_OF_DAY -> {
                                    Text(
                                        text = section.text,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = textColor,
                                        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                                    )
                                }
                                SectionType.ACTIVITY -> {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                    ) {
                                        Icon(
                                            imageVector = when {
                                                section.text.contains("Breakfast", ignoreCase = true) -> Icons.Default.Restaurant
                                                section.text.contains("Lunch", ignoreCase = true) -> Icons.Default.RestaurantMenu
                                                section.text.contains("Dinner", ignoreCase = true) -> Icons.Default.DinnerDining
                                                section.text.contains("Visit", ignoreCase = true) -> Icons.Default.Place
                                                section.text.contains("Explore", ignoreCase = true) -> Icons.Default.Explore
                                                else -> Icons.Default.FiberManualRecord
                                            },
                                            contentDescription = null,
                                            tint = accentGreen,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = section.text,
                                            fontSize = 14.sp,
                                            color = textColor,
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
                                SectionType.COORDINATES -> {
                                    Text(
                                        text = section.text,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = secondaryTextColor,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

enum class SectionType {
    DAY, TIME_OF_DAY, ACTIVITY, COORDINATES
}

data class ItinerarySection(val type: SectionType, val text: String)

fun formatItineraryText(text: String): List<ItinerarySection> {
    val sections = mutableListOf<ItinerarySection>()
    val lines = text.split("\n").filter { it.isNotBlank() }

    for (line in lines) {
        // Clean up markdown formatting
        val cleanedLine = line
            .replace("**", "")
            .replace("##", "")
            .replace("*", "")
            .replace("#", "")
            .trim()

        when {
            cleanedLine.startsWith("Day") && cleanedLine.contains(":") -> {
                sections.add(ItinerarySection(SectionType.DAY, cleanedLine))
            }
            cleanedLine.equals("Morning:", ignoreCase = true) ||
                    cleanedLine.equals("Afternoon:", ignoreCase = true) ||
                    cleanedLine.equals("Evening:", ignoreCase = true) -> {
                sections.add(ItinerarySection(SectionType.TIME_OF_DAY, cleanedLine))
            }
            cleanedLine.contains("Coordinates:", ignoreCase = true) -> {
                sections.add(ItinerarySection(SectionType.COORDINATES, cleanedLine))
            }
            cleanedLine.isNotEmpty() -> {
                sections.add(ItinerarySection(SectionType.ACTIVITY, cleanedLine))
            }
        }
    }

    return sections
}

fun extractCoordinates(text: String): ArrayList<LatLng> {
    val coordinates = ArrayList<LatLng>()
    val pattern = """(\d+\.\d+)°\s*N,\s*(\d+\.\d+)°\s*E""".toRegex()

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