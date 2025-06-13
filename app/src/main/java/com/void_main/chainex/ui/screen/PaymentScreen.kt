package com.void_main.chainex.ui.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.void_main.chainex.R
import com.void_main.chainex.Utils
import kotlinx.coroutines.delay

// Theme colors


@Composable
fun PaymentScreen() {
    val lightBackground = Color(0xFFF8F9FA)
    val accentGreen = Color(0xFF4CAF50)
    val buttonBlue = Color(0xFF1A73E8)
    val cardBg = Color(0xFFEAEEF1)
    val textColor = Color(0xFF202124)
    val secondaryTextColor = Color(0xFF5F6368)
    var amount by remember { mutableStateOf("") }
    var showSuccessAnimation by remember { mutableStateOf(false) }
    var showPaymentError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showProcessing by remember { mutableStateOf(false) }
    var note by remember { mutableStateOf("") }
    var showNoteDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as Activity

    // Function to process payment
    fun processPayment() {
        val paymentAmount = amount.toDoubleOrNull()

        if (paymentAmount == null || paymentAmount <= 0) {
            errorMessage = "Please enter a valid amount"
            showPaymentError = true
            return
        }

        if (paymentAmount > Utils.amountDeposited) {
            errorMessage = "Insufficient balance. Available: ₹${Utils.amountDeposited}"
            showPaymentError = true
            return
        }

        showProcessing = true
    }

    // Processing payment effect
    LaunchedEffect(showProcessing) {
        if (showProcessing) {
            delay(2000) // Simulate processing time

            val paymentAmount = amount.toDouble()
            Utils.amountDeposited = (Utils.amountDeposited - paymentAmount).toInt()

            showProcessing = false
            showSuccessAnimation = true

            // Auto dismiss and go back after success
            delay(3000)
            showSuccessAnimation = false
            activity.finish()
        }
    }

    // Success Animation Dialog
    if (showSuccessAnimation) {
        Dialog(onDismissRequest = { }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Success icon
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(accentGreen.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            modifier = Modifier.size(48.dp),
                            tint = accentGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Payment Successful!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "₹$amount sent to Bryce Miranda",
                        style = MaterialTheme.typography.bodyLarge,
                        color = secondaryTextColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Remaining Balance: ₹${Utils.amountDeposited}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = accentGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    // Processing Dialog
    if (showProcessing) {
        Dialog(onDismissRequest = { }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = accentGreen,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Processing Payment...",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = textColor
                    )
                }
            }
        }
    }

    // Add Note Dialog
    if (showNoteDialog) {
        AlertDialog(
            onDismissRequest = { showNoteDialog = false },
            title = {
                Text(
                    "Add Note",
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = { Text("Enter note...") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentGreen,
                        focusedLabelColor = accentGreen
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = { showNoteDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = accentGreen)
                ) {
                    Text("Save", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showNoteDialog = false }) {
                    Text("Cancel", color = secondaryTextColor)
                }
            },
            containerColor = Color.White
        )
    }

    // Error Dialog
    if (showPaymentError) {
        AlertDialog(
            onDismissRequest = { showPaymentError = false },
            title = {
                Text(
                    "Payment Error",
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            },
            text = { Text(errorMessage, color = secondaryTextColor) },
            confirmButton = {
                Button(
                    onClick = { showPaymentError = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                ) {
                    Text("OK", color = Color.White)
                }
            },
            containerColor = Color.White
        )
    }

    // Main UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .imePadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(
                    modifier = Modifier.padding(24.dp).align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Profile picture
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(accentGreen.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(40.dp),
                            tint = accentGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Name and banking info
                    Text(
                        text = "Paying Bryce Miranda",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Banking name: BRYCE MIRANDA",
                        color = secondaryTextColor,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "+91 86002 95685",
                        fontSize = 14.sp,
                        color = secondaryTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Amount Input Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Enter Amount",
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Amount input
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "₹",
                            fontSize = 48.sp,
                            color = accentGreen,
                            fontWeight = FontWeight.Bold
                        )

                        BasicTextField(
                            value = amount,
                            onValueChange = { newValue ->
                                // Only allow numbers and decimal point
                                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                                    amount = newValue
                                }
                            },
                            modifier = Modifier
                                .width(200.dp)
                                .padding(horizontal = 8.dp),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 48.sp,
                                textAlign = TextAlign.Center,
                                color = textColor,
                                fontWeight = FontWeight.Bold
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            singleLine = true,
                            decorationBox = { innerTextField ->
                                if (amount.isEmpty()) {
                                    Text(
                                        text = "0",
                                        fontSize = 48.sp,
                                        color = secondaryTextColor.copy(alpha = 0.5f),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Available balance
                    Text(
                        text = "Available: ₹${Utils.amountDeposited}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = secondaryTextColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Note section
                    if (note.isNotEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = cardBg),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Note,
                                    contentDescription = "Note",
                                    tint = accentGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = note,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = textColor,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { showNoteDialog = true },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Edit Note",
                                        tint = secondaryTextColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Add note button
                    if (note.isEmpty()) {
                        OutlinedButton(
                            onClick = { showNoteDialog = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = accentGreen
                            ),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = androidx.compose.foundation.BorderStroke(1.dp, accentGreen).brush
                            )
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add Note",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Add note")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Pay button
            val paymentAmount = amount.toDoubleOrNull() ?: 0.0
            Button(
                onClick = { processPayment() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (paymentAmount > 0) accentGreen else secondaryTextColor.copy(alpha = 0.3f),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(28.dp),
                enabled = paymentAmount > 0 && !showProcessing
            ) {
                if (showProcessing) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = if (paymentAmount > 0) "Pay ₹$amount" else "Enter amount",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PrevPayment(){
    PaymentScreen()
}