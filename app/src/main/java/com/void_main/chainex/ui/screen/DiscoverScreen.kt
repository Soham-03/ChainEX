package com.example.fiintechapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fiintechapp.ui.composables.ReviewDiscoverCardItem
import com.void_main.chainex.R
import com.void_main.chainex.ui.theme.Blackish_color


@Composable
fun DiscoverScreen() {

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), bottom = 110.dp)
                .verticalScroll(rememberScrollState())

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Discover")
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Mumbai", fontWeight = FontWeight.Bold)
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null
                        )
                    }
                }

                Button(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.phone_call),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "SOS", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.india_removebg_discover_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(130.dp),
                modifier = Modifier
                    .height(230.dp)
                    .padding(24.dp, 16.dp, 24.dp, 0.dp),
                // content padding
            ) {
                items(6) {

                    //card 1
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(100.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = "Cafe")
                            Image(
                                painter = painterResource(id = R.drawable.cafe),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                    //card 2
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(100.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = "Rentals")
                            Image(
                                painter = painterResource(id = R.drawable.icons8_bike_94),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }

                    //card 3

                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(100.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = "Tickets")
                            Image(
                                painter = painterResource(id = R.drawable.icons8_tickets_94),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }

                    //card 4
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(100.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = "Stay")
                            Image(
                                painter = painterResource(id = R.drawable.hotel),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }


                    //card 5

                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(100.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = "Wellness")
                            Image(
                                painter = painterResource(id = R.drawable.cosmetic),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }

                    //card 6

                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(100.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = "Tour Guides")
                            Image(
                                painter = painterResource(id = R.drawable.hotel),
                                contentDescription = null,
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Handle click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF00BCD4), Color(0xFF8BC34A)
                            ) // Gradient from blue to green
                        ), shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(text = "Explore Community")

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }



            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Blackish_color)
                    .padding(8.dp),
            ) {
                Text(
                    text = "People From Around The World \nLoves Us",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(16.dp)
                )

                LazyRow {
                    items(6) {
                        ReviewDiscoverCardItem()
                    }
                }
            }

        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDiscoverScreen() {
    DiscoverScreen()
}

