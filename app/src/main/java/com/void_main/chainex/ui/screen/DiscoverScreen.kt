package com.example.fiintechapp.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fiintechapp.ui.composables.ReviewDiscoverCardItem
import com.void_main.chainex.R
import com.void_main.chainex.activity.ItenaryActivity
import com.void_main.chainex.ui.theme.Blackish_color
import kotlinx.coroutines.delay


@Composable
fun DiscoverScreen() {
    var isVisible by remember { mutableStateOf(false) }
    var selectedCategoryIndex by remember { mutableStateOf(-1) }
    val textColor = Color(0xFF202124)
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
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
                val context = LocalContext.current
                Button(onClick = {
                    val phone_intent = Intent(Intent.ACTION_CALL)
                    // Set data of Intent through Uri by parsing phone number
                    phone_intent.setData(Uri.parse("tel:9307377878"))


                    // start Intent
                    context.startActivity(phone_intent)
                }) {
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


            Text(
                text = "Explore Categories",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 16.dp)
            )

            val categories = listOf(
                CategoryItem("Cafe", R.drawable.cafe, Color(0xFFFF6B6B)),
                CategoryItem("Rentals", R.drawable.icons8_bike_94, Color(0xFF4ECDC4)),
                CategoryItem("Tickets", R.drawable.icons8_tickets_94, Color(0xFF45B7D1)),
                CategoryItem("Stay", R.drawable.hotel, Color(0xFFFFBE0B)),
                CategoryItem("Wellness", R.drawable.cosmetic, Color(0xFFF72585)),
                CategoryItem("Tour Guides", R.drawable.hotel, Color(0xFF7209B7))
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .height(250.dp)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(categories) { index, category ->
                    CategoryCard(
                        category = category,
                        isSelected = selectedCategoryIndex == index,
                        onClick = { selectedCategoryIndex = index },
                        animationDelay = index * 100
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            val context = LocalContext.current
            Button(
                onClick = {
                    val intent = Intent(context, ItenaryActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
                    .height(50.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF00BCD4), Color(0xFF8BC34A)
                            ) // Gradient from blue to green
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(text = "Get Itenary" )
                Spacer(Modifier.width(6.dp))
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

data class CategoryItem(
    val name: String,
    val icon: Int,
    val color: Color
)

@Composable
fun CategoryCard(
    category: CategoryItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    animationDelay: Int
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(animationDelay.toLong())
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,

    ) {
        Card(
            modifier = Modifier
                .size(110.dp)
                .clickable(onClick = onClick)
                .then(
                    if (isSelected) Modifier.border(
                        2.dp,
                        category.color,
                        RoundedCornerShape(16.dp)
                    ) else Modifier
                ),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            category.color.copy(alpha = 0.1f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = category.icon),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = category.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF202124)
                )
            }
        }
    }
}

@Composable
fun AnimatedReviewCard(index: Int) {
//    var isVisible by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay((index * 200).toLong())
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
        exit = fadeOut() + slideOutHorizontally()
    ) {
        ReviewDiscoverCardItem()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDiscoverScreen() {
    DiscoverScreen()
}

