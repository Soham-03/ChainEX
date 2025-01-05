package com.void_main.chainex.ui.screen

import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.void_main.chainex.R
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.void_main.chainex.Transaction
import com.void_main.chainex.TransactionStatus
import com.void_main.chainex.Utils

@Composable
fun PaymentScreen() {
    var amount by remember { mutableIntStateOf(0) }
    var showSuccessAnimation by remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (showSuccessAnimation) {
        // Success Animation Dialog
        Dialog(onDismissRequest = { showSuccessAnimation = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // You'll need to add a payment_success.json animation file to your raw resources
                    LottieAnimation(
                        composition = rememberLottieComposition(
                            spec = LottieCompositionSpec.RawRes(R.raw.success)
                        ).value,
                        iterations = 1,
                        modifier = Modifier.size(200.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Payment Successful!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Main container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile picture
        Image(
            painter = painterResource(id = R.drawable.bitcoin),
             "Profile Picture",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Name and banking info
        Text(
            text = "Paying Sanket Mane",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Banking name: SANKET T MANE",
            color = Color.Gray,
            fontSize = 14.sp
        )

        // Phone number
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "+91 86002 95685",
            fontSize = 14.sp
        )

        // Input amount
        Spacer(modifier = Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "₹",
                fontSize = 40.sp,
            )
            BasicTextField(
                value = amount.toString(),
                onValueChange = {
                    // Handle only valid integer input
                    amount = it.toIntOrNull() ?: 0
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(60.dp)
                    .background(Color.Transparent),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Add note button
        Button(
            onClick = { /* TODO: Add note action */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(text = "Add note", color = Color.White)
        }

        Button(
            onClick = {
                // Simulate payment processing
                showSuccessAnimation = true
                Utils.transactions.add(
                    Transaction(amount, "Sanket Mane", "0x1234...5678", TransactionStatus.SUCCESS)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Pay ₹$amount",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
