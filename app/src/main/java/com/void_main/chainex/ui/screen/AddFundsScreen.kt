package com.void_main.chainex.ui.screen

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.razorpay.Checkout
import com.void_main.chainex.MainActivity
import org.json.JSONObject
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFundsScreen() {
    // Define the app colors - light theme
    val lightBackground = Color(0xFFF8F9FA)
    val accentGreen = Color(0xFF4CAF50)
    val buttonBlue = Color(0xFF1A73E8)
    val cardBg = Color(0xFFFFFFFF)
    val borderGray = Color(0xFFE0E0E0)
    val textColor = Color(0xFF202124)
    val secondaryTextColor = Color(0xFF5F6368)
    val warningColor = Color(0xFFF57F17)
    val negativeColor = Color(0xFFE53935)

    // State variables
    var amount by remember { mutableStateOf("200") }
    var selectedCurrency by remember { mutableStateOf("USD") }
    var currentScreen by remember { mutableStateOf("amount") } // "amount", "breakdown"
    var isProcessing by remember { mutableStateOf(false) }

    // Conversion rates and fees (would normally come from an API)
    val usdToInrRate = 82.5f
    val eurToInrRate = 89.75f
    val chainlinkOracleRate = if (selectedCurrency == "USD") 82.42f else 89.68f
    val rateSpread = if (selectedCurrency == "USD") 0.08f else 0.07f

    // Calculate conversion
    val conversionRate = if (selectedCurrency == "USD") usdToInrRate else eurToInrRate
    val amountValue = amount.toFloatOrNull() ?: 0f
    val convertedAmount = amountValue * conversionRate

    // Calculate fees
    val serviceCharge = convertedAmount * 0.015f
    val gstOnService = serviceCharge * 0.18f
    val networkFee = 25f
    val spreadAmount = amountValue * rateSpread
    val platformFee = convertedAmount * 0.01f
    val tdsCharge = if (convertedAmount > 10000) convertedAmount * 0.01f else 0f
    val internationalProcessingFee = 35f

    // Total deductions
    val totalFees = serviceCharge + gstOnService + networkFee + spreadAmount + platformFee + tdsCharge + internationalProcessingFee
    val finalAmount = convertedAmount - totalFees

    // Format currency values
    val formatInr = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    val formatForeign = NumberFormat.getCurrencyInstance(
        if (selectedCurrency == "USD") Locale.US else Locale.GERMANY
    )

    // Context for Razorpay
    val context = LocalContext.current
    val activity = context as Activity

    // Initialize Razorpay Checkout
    val checkout = Checkout()
    checkout.setKeyID("rzp_test_qaxROdR325sQgT") // Replace with your Razorpay key

    // Function to start Razorpay payment
    fun startRazorpayPayment() {
        try {
            val options = JSONObject()

            options.put("name", "ChainEx")
            options.put("description", "Add Funds to Wallet")
            options.put("currency", "INR")
            options.put("amount", (finalAmount * 100).toInt())

            options.put("prefill", JSONObject().apply {
                put("email", "sanketmane2323@gmail.com")
                put("contact", "8600295685")
            })

            options.put("theme", JSONObject().apply {
                put("color", "#1A73E8")
            })

            val notes = JSONObject()
            notes.put("original_amount", "${selectedCurrency} ${amount}")
            notes.put("conversion_rate", conversionRate)
            notes.put("total_fees", totalFees)
            options.put("notes", notes)

            checkout.open(activity, options)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "Add Funds",
                fontSize = 24.sp,
                color = textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Currency and Amount Selection Screen
            AnimatedVisibility(
                visible = currentScreen == "amount",
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Choose Currency",
                            fontSize = 16.sp,
                            color = textColor,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Currency selection
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            CurrencyOption(
                                currencyCode = "USD",
                                currencySymbol = "$",
                                isSelected = selectedCurrency == "USD",
                                onClick = { selectedCurrency = "USD" },
                                accentColor = accentGreen,
                                textColor = textColor
                            )

                            CurrencyOption(
                                currencyCode = "EUR",
                                currencySymbol = "€",
                                isSelected = selectedCurrency == "EUR",
                                onClick = { selectedCurrency = "EUR" },
                                accentColor = accentGreen,
                                textColor = textColor
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Amount input
                        Text(
                            text = "Enter Amount",
                            fontSize = 16.sp,
                            color = textColor,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = amount,
                            onValueChange = {
                                if (it.isEmpty() || it.matches(Regex("^\\d+(\\.\\d{0,2})?\$"))) {
                                    amount = it
                                }
                            },
                            leadingIcon = {
                                Text(
                                    text = if (selectedCurrency == "USD") "$" else "€",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = accentGreen
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textColor,
                                unfocusedTextColor = textColor,
                                cursorColor = accentGreen,
                                focusedBorderColor = accentGreen,
                                unfocusedBorderColor = borderGray
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "≈ ${formatInr.format(convertedAmount)}",
                            fontSize = 16.sp,
                            color = secondaryTextColor
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Includes fees & taxes",
                                fontSize = 12.sp,
                                color = secondaryTextColor
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Fee Info",
                                tint = secondaryTextColor,
                                modifier = Modifier.size(12.dp)
                            )
                        }

                        Text(
                            text = "Final amount: ${formatInr.format(finalAmount)}",
                            fontSize = 14.sp,
                            color = accentGreen,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { currentScreen = "breakdown" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonBlue
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text(
                                text = "CONTINUE TO PAYMENT",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Transaction Breakdown Screen
            AnimatedVisibility(
                visible = currentScreen == "breakdown",
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Transaction Breakdown",
                            fontSize = 18.sp,
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        BreakdownRow(
                            label = "Amount (${selectedCurrency})",
                            value = formatForeign.format(amountValue),
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        BreakdownRow(
                            label = "Exchange Rate",
                            value = "1 ${selectedCurrency} = ₹${String.format("%.2f", conversionRate)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        BreakdownRow(
                            label = "Converted Amount",
                            value = formatInr.format(convertedAmount),
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Fees & Charges",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = textColor
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        BreakdownRow(
                            label = "Service Charge (1.5%)",
                            value = "- ${formatInr.format(serviceCharge)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        BreakdownRow(
                            label = "GST on Service (18%)",
                            value = "- ${formatInr.format(gstOnService)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        BreakdownRow(
                            label = "Platform Fee (1%)",
                            value = "- ${formatInr.format(platformFee)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        if (tdsCharge > 0) {
                            BreakdownRow(
                                label = "TDS (1%)",
                                value = "- ${formatInr.format(tdsCharge)}",
                                textColor = textColor,
                                secondaryTextColor = secondaryTextColor,
                                valueColor = negativeColor
                            )
                        }

                        BreakdownRow(
                            label = "Network Fee",
                            value = "- ${formatInr.format(networkFee)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        BreakdownRow(
                            label = "International Processing Fee",
                            value = "- ${formatInr.format(internationalProcessingFee)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        BreakdownRow(
                            label = "Exchange Rate Spread",
                            value = "- ${formatInr.format(spreadAmount)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        BreakdownRow(
                            label = "Total Fees & Taxes",
                            value = "- ${formatInr.format(totalFees)}",
                            textColor = textColor,
                            secondaryTextColor = warningColor,
                            valueColor = warningColor
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Divider(color = borderGray)

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Amount to Pay",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )

                            Text(
                                text = formatInr.format(finalAmount),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = accentGreen
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                isProcessing = true
                                startRazorpayPayment()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonBlue
                            ),
                            enabled = !isProcessing,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            if (isProcessing) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text(
                                    text = "PAY WITH RAZORPAY",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        TextButton(
                            onClick = { currentScreen = "amount" },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = "Go Back",
                                color = secondaryTextColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyOption(
    currencyCode: String,
    currencySymbol: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    accentColor: Color,
    textColor: Color
) {
    val backgroundColor = if (isSelected) accentColor.copy(alpha = 0.1f) else Color.Transparent
    val borderColor = if (isSelected) accentColor else Color.LightGray
    val contentColor = if (isSelected) accentColor else textColor

    Box(
        modifier = Modifier
            .size(100.dp, 80.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currencySymbol,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = currencyCode,
                fontSize = 14.sp,
                color = contentColor
            )
        }
    }
}

@Composable
fun BreakdownRow(
    label: String,
    value: String,
    textColor: Color,
    secondaryTextColor: Color,
    valueColor: Color = textColor
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = secondaryTextColor
        )

        Text(
            text = value,
            fontSize = 14.sp,
            color = valueColor,
            fontWeight = FontWeight.Medium
        )
    }
}

// Helper function to get current time formatted
private fun getCurrentTimeFormatted(): String {
    val sdf = SimpleDateFormat("HH:mm, dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date())
}