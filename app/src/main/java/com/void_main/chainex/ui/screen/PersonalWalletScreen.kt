package com.void_main.chainex.ui.screen



import android.content.Intent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.void_main.chainex.AddFundsActivity
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.*
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalWalletScreen(
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

    // State for animations
    var isLoading by remember { mutableStateOf(true) }
    var animationProgress by remember { mutableFloatStateOf(0f) }
    var selectedCurrency by remember { mutableStateOf("USD") }

    // Currency data
    val currentBalance = 15801.45
    val usdToInrRates = remember { generateRandomRates(81.5f, 83.5f) }
    val eurToInrRates = remember { generateRandomRates(88.5f, 91.5f) }

    // Format currency
    val formatInr = NumberFormat.getCurrencyInstance(Locale("en", "IN"))

    // Animation
    LaunchedEffect(Unit) {
        delay(1000) // Simulate loading
        isLoading = false
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        ) { value, _ ->
            animationProgress = value
        }
    }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Personal Wallet",
                        color = Color.White
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
            // Total Wealth Section
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Wealth",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )

                    if (isLoading) {
                        CircularProgressIndicator(
                            color = accentGreen,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else {
                        Text(
                            text = formatInr.format(currentBalance),
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

            // Currency Graph Section
            Card(
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Exchange Rates",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    // Currency selector
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CurrencyTab(
                            text = "USD/INR",
                            isSelected = selectedCurrency == "USD",
                            onClick = { selectedCurrency = "USD" },
                            accentColor = accentGreen,
                            textColor = textColor
                        )
                        CurrencyTab(
                            text = "EUR/INR",
                            isSelected = selectedCurrency == "EUR",
                            onClick = { selectedCurrency = "EUR" },
                            accentColor = accentGreen,
                            textColor = textColor
                        )
                    }

                    // Current rate display
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Current Rate",
                                fontSize = 14.sp,
                                color = secondaryTextColor
                            )
                            Text(
                                text = if (selectedCurrency == "USD")
                                    "₹${usdToInrRates.last()}" else "₹${eurToInrRates.last()}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }

                        Text(
                            text = if (selectedCurrency == "USD") "+0.25%" else "+0.18%",
                            fontSize = 14.sp,
                            color = accentGreen,
                            modifier = Modifier
                                .background(
                                    color = accentGreen.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Graph
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = accentGreen
                            )
                        } else {
                            AnimatedGraph(
                                data = if (selectedCurrency == "USD") usdToInrRates else eurToInrRates,
                                animationProgress = animationProgress,
                                color = accentGreen,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    // Time labels
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("Aug", "Sep", "Oct", "Nov", "Dec", "Jan").forEach { month ->
                            Text(
                                text = month,
                                fontSize = 12.sp,
                                color = secondaryTextColor
                            )
                        }
                    }
                }
            }

            // Account Details Card
            Card(
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Account Details",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Current Balance
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Current Balance",
                                fontSize = 14.sp,
                                color = secondaryTextColor
                            )
                            Text(
                                text = formatInr.format(currentBalance),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = null,
                            tint = accentGreen,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = borderGray
                    )

                    // Account Info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Account Status",
                            fontSize = 14.sp,
                            color = secondaryTextColor
                        )
                        Text(
                            text = "Active",
                            fontSize = 14.sp,
                            color = accentGreen,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Last Updated",
                            fontSize = 14.sp,
                            color = secondaryTextColor
                        )
                        Text(
                            text = "Just now",
                            fontSize = 14.sp,
                            color = textColor
                        )
                    }
                }
            }

            // Add Funds Button
            Button(
                onClick = {
                    val intent = Intent(context, AddFundsActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonBlue
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Add Funds",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CurrencyTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    accentColor: Color,
    textColor: Color
) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = if (isSelected) accentColor else textColor.copy(alpha = 0.7f),
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    )
}

@Composable
fun AnimatedGraph(
    data: List<Float>,
    animationProgress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val stepX = width / (data.size - 1)

        val min = data.minOrNull() ?: 0f
        val max = data.maxOrNull() ?: 1f
        val range = max - min

        val path = Path()
        val visiblePoints = (data.size * animationProgress).toInt()

        if (visiblePoints > 0) {
            data.take(visiblePoints).forEachIndexed { index, value ->
                val x = index * stepX
                val y = height - ((value - min) / range * height * 0.8f) - height * 0.1f

                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    val prevX = (index - 1) * stepX
                    val prevY = height - ((data[index - 1] - min) / range * height * 0.8f) - height * 0.1f

                    val controlX1 = prevX + stepX / 3
                    val controlY1 = prevY
                    val controlX2 = x - stepX / 3
                    val controlY2 = y

                    path.cubicTo(controlX1, controlY1, controlX2, controlY2, x, y)
                }
            }

            // Draw the line
            drawPath(
                path = path,
                color = color,
                style = Stroke(width = 3.dp.toPx())
            )

            // Draw dots at data points
            data.take(visiblePoints).forEachIndexed { index, value ->
                val x = index * stepX
                val y = height - ((value - min) / range * height * 0.8f) - height * 0.1f

                drawCircle(
                    color = color,
                    radius = 4.dp.toPx(),
                    center = Offset(x, y)
                )

                drawCircle(
                    color = Color.White,
                    radius = 2.dp.toPx(),
                    center = Offset(x, y)
                )
            }
        }
    }
}

fun generateRandomRates(baseRate: Float, maxRate: Float): List<Float> {
    return List(6) { index ->
        val progress = index.toFloat() / 5f
        baseRate + (maxRate - baseRate) * progress + Random.nextFloat() * 0.5f
    }
}

@Composable
fun PreviewPersonalWalletScreen() {
    PersonalWalletScreen()
}