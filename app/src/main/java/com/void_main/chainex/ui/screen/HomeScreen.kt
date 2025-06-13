package com.void_main.chainex.ui.screen

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.void_main.chainex.AddFundsActivity
import com.void_main.chainex.R
import com.void_main.chainex.Utils
import com.void_main.chainex.activity.BillActivity
import com.void_main.chainex.activity.PersonalWalletActivity
import com.void_main.chainex.activity.ProfileActivity
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val barCodeScanner = com.void_main.chainex.util.BarcodeScanner(context)

    // State for balance visibility
    var isBalanceVisible by remember { mutableStateOf(false) }
    val balanceAmount = "₹12,580.45"

    // Format currency values
    val formatInr = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    val walletBalance = Utils.amountDeposited

    // Animation for balance visibility
    val visibilityIconRotation by animateFloatAsState(
        targetValue = if (isBalanceVisible) 0f else 180f,
        animationSpec = tween(300)
    )

    // Background colors
    val lightBackground = Color(0xFFF8F9FA)
    val accentGreen = Color(0xFF4CAF50)
    val buttonBlue = Color(0xFF1A73E8)
    val cardBg = Color(0xFFFFFFFF)
    val textColor = Color(0xFF202124)
    val secondaryTextColor = Color(0xFF5F6368)

    Column(
        Modifier
            .imePadding()
            .background(lightBackground)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
        ) {
            // Top Section with Gradient Background
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                // Profile and Notification
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ChainEx",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Row {
                        IconButton(
                            onClick = { /* Notification */ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                val intent = Intent(context, ProfileActivity::class.java)
                                context.startActivity(intent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                // Balance Card
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF151515)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF333333)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Wallet Balance",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            AnimatedVisibility(
                                visible = isBalanceVisible,
                                enter = fadeIn(animationSpec = tween(300)),
                                exit = fadeOut(animationSpec = tween(300))
                            ) {
                                Text(
                                    text = formatInr.format(walletBalance),
                                    color = accentGreen,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            AnimatedVisibility(
                                visible = !isBalanceVisible,
                                enter = fadeIn(animationSpec = tween(300)),
                                exit = fadeOut(animationSpec = tween(300))
                            ) {
                                Text(
                                    text = "₹ • • • • •",
                                    color = accentGreen,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            IconButton(
                                onClick = { isBalanceVisible = !isBalanceVisible }
                            ) {
                                Icon(
                                    imageVector = if (isBalanceVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (isBalanceVisible) "Hide Balance" else "Show Balance",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .graphicsLayer { rotationY = visibilityIconRotation }
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    val intent = Intent(context, AddFundsActivity::class.java)
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonBlue
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add Money")
                            }

                            Button(
                                onClick = { /* History */ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.White
                                ),
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("History")
                            }
                        }
                    }
                }

                // SIM Verification Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "VERIFY YOUR SIM",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )

                        Text(
                            text = "To activate your\nUPI Payments",
                            fontSize = 22.sp,
                            color = accentGreen,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                        )

                        Button (
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                            ),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.5.dp, accentGreen)
                        ) {
                            Text("VERIFY SIM")
                        }
                    }

                    Image(
                        painter = painterResource(R.drawable.sim_card),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(start = 8.dp)
                    )
                }
            }

            // Search Bar
            Card(
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = secondaryTextColor,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(24.dp)
                    )

                    Text(
                        text = "Search name, UPI ID or Number",
                        fontSize = 16.sp,
                        color = secondaryTextColor
                    )
                }
            }

            // Quick Actions
            Text(
                text = "Quick Actions",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val scope = rememberCoroutineScope()
                val barcodeRes = barCodeScanner.barCodeRes.collectAsStateWithLifecycle()

                // Scan QR Button
                QuickActionButton(
                    icon = R.drawable.scan_qr_code,
                    label = "Scan QR",
                    onClick = {
                        scope.launch {
                            barCodeScanner.startScan()
                            barcodeRes.value.toString()
                        }
                    }
                )

                // Add Funds Button
                QuickActionButton(
                    icon = R.drawable.wallet,
                    label = "Add Funds",
                    onClick = {
                        val intent = Intent(context, AddFundsActivity::class.java)
                        context.startActivity(intent)
                    }
                )

                // Bills Button
                QuickActionButton(
                    icon = R.drawable.receipt_text,
                    label = "Track Bills",
                    onClick = {
                        val intent = Intent(context, BillActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }

            // Personal Wallet Card
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = cardBg
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        val intent = Intent(context, PersonalWalletActivity::class.java)
                        context.startActivity(intent)

                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.wallet),
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(24.dp)
                    )

                    Text(
                        text = "Personal Wallet",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = textColor,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = secondaryTextColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Invite Friends Banner
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { /* Invite Friends */ }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Invite a friend and get Rs 400",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )

                        Text(
                            text = "For every friend who joins and completes their KYC, you'll earn Rs 400 and they'll earn Rs 25",
                            fontSize = 14.sp,
                            color = secondaryTextColor,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Button(
                            onClick = { /* Invite */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = accentGreen
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Invite Now")
                        }
                    }

                    Image(
                        painter = painterResource(id = R.drawable.invite_friend_image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(start = 8.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(Modifier.height(100.dp))
        }
    }
}

@Composable
fun QuickActionButton(
    icon: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            shape = CircleShape,
            onClick = onClick,
            border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            modifier = Modifier.size(64.dp)
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = label,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF202124),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen()
}