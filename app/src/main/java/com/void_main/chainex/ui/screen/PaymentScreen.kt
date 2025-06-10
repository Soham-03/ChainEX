package com.void_main.chainex.ui.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.razorpay.Checkout
import com.void_main.chainex.R
import com.void_main.chainex.Transaction
import com.void_main.chainex.TransactionStatus
import com.void_main.chainex.Utils
import org.json.JSONObject

@Composable
fun PaymentScreen() {
    var amount by remember { mutableIntStateOf(0) }
    var showSuccessAnimation by remember { mutableStateOf(false) }
    var showPaymentError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = context as Activity

    // Initialize Razorpay Checkout
    val checkout = Checkout()
    checkout.setKeyID("") // Replace with your Razorpay key

    // Function to start Razorpay payment
    fun startRazorpayPayment() {
        try {
            val options = JSONObject()

            // Basic payment options
            options.put("name", "ChainEx")
            options.put("description", "Payment to Sanket Mane")
            options.put("currency", "INR")
            options.put("amount", amount * 100) // Razorpay expects amount in paise

            // Prefill customer details
            options.put("prefill", JSONObject().apply {
                put("email", "user@example.com") // Replace with actual user email
                put("contact", "9999999999") // Replace with actual user phone
            })

            // Add theme
            options.put("theme", JSONObject().apply {
                put("color", "#000000") // Black theme
            })

            // Add notes
            val notes = JSONObject()
            notes.put("recipient", "Sanket Mane")
            notes.put("purpose", "Wallet Transfer")
            options.put("notes", notes)

            checkout.open(activity, options)

        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = "Failed to initiate payment: ${e.message}"
            showPaymentError = true
        }
    }

    // Success Animation Dialog
    if (showSuccessAnimation) {
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

    // Error Dialog
    if (showPaymentError) {
        AlertDialog(
            onDismissRequest = { showPaymentError = false },
            title = { Text("Payment Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                Button(onClick = { showPaymentError = false }) {
                    Text("OK")
                }
            }
        )
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
            contentDescription = "Profile Picture",
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

        // Pay button
        Button(
            onClick = {
                if (amount > 0) {
                    startRazorpayPayment()
                } else {
                    errorMessage = "Please enter a valid amount"
                    showPaymentError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = amount > 0
        ) {
            Text(
                text = if (amount > 0) "Pay ₹$amount" else "Enter amount",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}