package com.void_main.chainex.ui.screen

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.void_main.chainex.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
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
    var currentScreen by remember { mutableStateOf("amount") } // "amount", "card", "breakdown"
    var isProcessing by remember { mutableStateOf(false) }
    var processingComplete by remember { mutableStateOf(false) }

    // Credit card form states
    var cardNumber by remember { mutableStateOf("") }
    var cardName by remember { mutableStateOf("") }
    var expiryMonth by remember { mutableStateOf("") }
    var expiryYear by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    // Conversion rates and fees (would normally come from an API)
    val usdToInrRate = 82.5f
    val eurToInrRate = 89.75f
    val chainlinkOracleRate = if (selectedCurrency == "USD") 82.42f else 89.68f // Simulated chainlink oracle rate
    val rateSpread = if (selectedCurrency == "USD") 0.08f else 0.07f // Difference between our rate and chainlink

    // Calculate conversion based on selected currency
    val conversionRate = if (selectedCurrency == "USD") usdToInrRate else eurToInrRate
    val amountValue = amount.toFloatOrNull() ?: 0f
    val convertedAmount = amountValue * conversionRate

    // Calculate all fees
    val serviceCharge = convertedAmount * 0.015f // 1.5% service charge
    val gstOnService = serviceCharge * 0.18f // 18% GST on service charge
    val networkFee = 25f // Fixed network fee in INR
    val spreadAmount = amountValue * rateSpread // Rate spread amount
    val platformFee = convertedAmount * 0.01f // 1% platform fee
    val tdsCharge = if (convertedAmount > 10000) convertedAmount * 0.01f else 0f // 1% TDS for amounts > 10,000 INR
    val internationalProcessingFee = 35f // Fixed international processing fee

    // Total deductions
    val totalFees = serviceCharge + gstOnService + networkFee + spreadAmount + platformFee + tdsCharge + internationalProcessingFee
    val finalAmount = convertedAmount - totalFees

    // Format currency values
    val formatInr = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    val formatForeign = NumberFormat.getCurrencyInstance(
        if (selectedCurrency == "USD") Locale.US else Locale.GERMANY
    )

    // Animation for the payment success
    val coroutineScope = rememberCoroutineScope()

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
                                // Only allow numeric input with up to 2 decimal places
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

                        // Show approximate conversion
                        Text(
                            text = "≈ ${formatInr.format(convertedAmount)}",
                            fontSize = 16.sp,
                            color = secondaryTextColor
                        )

                        // Show fee information
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

                        Spacer(modifier = Modifier.height(8.dp))

                        // Exchange rate info with Chainlink
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "Info",
                                        tint = secondaryTextColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Current Exchange Rate",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = textColor
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "1 $selectedCurrency = ₹${String.format("%.2f", conversionRate)}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textColor
                                )

                                Text(
                                    text = "Chainlink Oracle: 1 $selectedCurrency = ₹${String.format("%.2f", chainlinkOracleRate)}",
                                    fontSize = 12.sp,
                                    color = secondaryTextColor
                                )

                                Text(
                                    text = "Updated: ${getCurrentTimeFormatted()}",
                                    fontSize = 11.sp,
                                    color = secondaryTextColor.copy(alpha = 0.7f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { currentScreen = "card" },
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

            // Credit Card Form Screen
            AnimatedVisibility(
                visible = currentScreen == "card",
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
                            text = "Payment Details",
                            fontSize = 18.sp,
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Adding ${formatForeign.format(amountValue)} to your wallet",
                            fontSize = 14.sp,
                            color = secondaryTextColor
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Card number
                        OutlinedTextField(
                            value = cardNumber,
                            onValueChange = { newValue ->
                                // Format credit card number with spaces every 4 digits
                                if (newValue.count { it == ' ' } <= 3 && newValue.length <= 19) {
                                    val digitsOnly = newValue.filter { it.isDigit() }
                                    if (digitsOnly.length <= 16) {
                                        cardNumber = digitsOnly.chunked(4).joinToString(" ")
                                    }
                                }
                            },
                            label = { Text("Card Number") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.CreditCard,
                                    contentDescription = "Card",
                                    tint = secondaryTextColor
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textColor,
                                unfocusedTextColor = textColor,
                                cursorColor = accentGreen,
                                focusedBorderColor = accentGreen,
                                unfocusedBorderColor = borderGray,
                                focusedLabelColor = accentGreen,
                                unfocusedLabelColor = secondaryTextColor
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            placeholder = { Text("1234 5678 9012 3456") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Cardholder name
                        OutlinedTextField(
                            value = cardName,
                            onValueChange = { cardName = it },
                            label = { Text("Cardholder Name") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Person",
                                    tint = secondaryTextColor
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textColor,
                                unfocusedTextColor = textColor,
                                cursorColor = accentGreen,
                                focusedBorderColor = accentGreen,
                                unfocusedBorderColor = borderGray,
                                focusedLabelColor = accentGreen,
                                unfocusedLabelColor = secondaryTextColor
                            ),
                            placeholder = { Text("John Smith") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Expiry date (improved) and CVV in a row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Month dropdown
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Expiry Date",
                                    fontSize = 12.sp,
                                    color = secondaryTextColor,
                                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    // Month field
                                    OutlinedTextField(
                                        value = expiryMonth,
                                        onValueChange = {
                                            if (it.length <= 2 && it.all { char -> char.isDigit() }) {
                                                val asInt = it.toIntOrNull() ?: 0
                                                if (it.isEmpty() || (asInt in 1..12)) {
                                                    expiryMonth = it
                                                }
                                            }
                                        },
                                        placeholder = { Text("MM") },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = textColor,
                                            unfocusedTextColor = textColor,
                                            cursorColor = accentGreen,
                                            focusedBorderColor = accentGreen,
                                            unfocusedBorderColor = borderGray
                                        ),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.weight(1f),
                                        textStyle = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center)
                                    )

                                    Text(
                                        text = "/",
                                        fontSize = 20.sp,
                                        color = textColor,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )

                                    // Year field
                                    OutlinedTextField(
                                        value = expiryYear,
                                        onValueChange = {
                                            if (it.length <= 2 && it.all { char -> char.isDigit() }) {
                                                expiryYear = it
                                            }
                                        },
                                        placeholder = { Text("YY") },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = textColor,
                                            unfocusedTextColor = textColor,
                                            cursorColor = accentGreen,
                                            focusedBorderColor = accentGreen,
                                            unfocusedBorderColor = borderGray
                                        ),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.weight(1f),
                                        textStyle = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center)
                                    )
                                }
                            }

                            // CVV field
                            Column(modifier = Modifier.weight(0.8f)) {
                                Text(
                                    text = "CVV",
                                    fontSize = 12.sp,
                                    color = secondaryTextColor,
                                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                                )

                                OutlinedTextField(
                                    value = cvv,
                                    onValueChange = {
                                        if (it.length <= 3 && it.all { char -> char.isDigit() }) {
                                            cvv = it
                                        }
                                    },
                                    placeholder = { Text("123") },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = textColor,
                                        unfocusedTextColor = textColor,
                                        cursorColor = accentGreen,
                                        focusedBorderColor = accentGreen,
                                        unfocusedBorderColor = borderGray
                                    ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.fillMaxWidth(),
                                    textStyle = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Secure payment information
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Secure",
                                tint = accentGreen,
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Your payment info is secured with 256-bit encryption",
                                fontSize = 12.sp,
                                color = secondaryTextColor
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { currentScreen = "breakdown" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonBlue
                            ),
                            enabled = cardNumber.replace(" ", "").length == 16 &&
                                    cardName.isNotEmpty() &&
                                    expiryMonth.isNotEmpty() &&
                                    expiryYear.isNotEmpty() &&
                                    cvv.length == 3,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text(
                                text = "PROCEED TO CONFIRMATION",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
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

                        // Original amount
                        BreakdownRow(
                            label = "Amount (${selectedCurrency})",
                            value = formatForeign.format(amountValue),
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Exchange rate with ChainLink oracle info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Exchange Rate",
                                    fontSize = 14.sp,
                                    color = secondaryTextColor
                                )

                                Text(
                                    text = "via Chainlink Oracle",
                                    fontSize = 12.sp,
                                    color = secondaryTextColor.copy(alpha = 0.7f)
                                )
                            }

                            Text(
                                text = "1 ${selectedCurrency} = ₹${String.format("%.2f", conversionRate)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = textColor
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Converted amount
                        BreakdownRow(
                            label = "Converted Amount",
                            value = formatInr.format(convertedAmount),
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Fees Section Header
                        Text(
                            text = "Fees & Charges",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = textColor
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Service charge
                        BreakdownRow(
                            label = "Service Charge (1.5%)",
                            value = "- ${formatInr.format(serviceCharge)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        // GST on service charge
                        BreakdownRow(
                            label = "GST on Service (18%)",
                            value = "- ${formatInr.format(gstOnService)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        // Platform Fee
                        BreakdownRow(
                            label = "Platform Fee (1%)",
                            value = "- ${formatInr.format(platformFee)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        // TDS (if applicable)
                        if (tdsCharge > 0) {
                            BreakdownRow(
                                label = "TDS (1%)",
                                value = "- ${formatInr.format(tdsCharge)}",
                                textColor = textColor,
                                secondaryTextColor = secondaryTextColor,
                                valueColor = negativeColor
                            )
                        }

                        // Network fee
                        BreakdownRow(
                            label = "Blockchain Network Fee",
                            value = "- ${formatInr.format(networkFee)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        // International Processing Fee
                        BreakdownRow(
                            label = "International Processing Fee",
                            value = "- ${formatInr.format(internationalProcessingFee)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        // Exchange rate spread
                        BreakdownRow(
                            label = "Exchange Rate Spread",
                            value = "- ${formatInr.format(spreadAmount)}",
                            textColor = textColor,
                            secondaryTextColor = secondaryTextColor,
                            valueColor = negativeColor
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Total fees
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

                        // Final amount
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Amount Added to Wallet",
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

                        Spacer(modifier = Modifier.height(4.dp))

                        // Conversion Summary
                        Text(
                            text = "${formatForeign.format(amountValue)} = ${formatInr.format(finalAmount)}",
                            fontSize = 14.sp,
                            color = secondaryTextColor,
                            modifier = Modifier.align(Alignment.End)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Card info summary
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CreditCard,
                                    contentDescription = "Card",
                                    tint = secondaryTextColor,
                                    modifier = Modifier.size(20.dp)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = "**** **** **** ${cardNumber.takeLast(4)}",
                                        fontSize = 14.sp,
                                        color = textColor,
                                        fontWeight = FontWeight.Medium
                                    )

                                    Text(
                                        text = "Exp: ${expiryMonth.padStart(2, '0')}/${expiryYear.padStart(2, '0')}",
                                        fontSize = 12.sp,
                                        color = secondaryTextColor
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        val context = LocalContext.current
                        // Add Funds button
                        Button(
                            onClick = {
                                isProcessing = true
                                coroutineScope.launch {
                                    // Simulate processing
                                    delay(2000)
                                    isProcessing = false
                                    processingComplete = true
                                    delay(1500)
                                    // Reset form for demo purposes
                                    // Reset form for demo purposes
                                    val intent = Intent(context, MainActivity::class.java)
                                    // You can add flags if needed, for example to clear the back stack
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                    context.startActivity(intent)
                                    currentScreen = "amount"
                                    processingComplete = false
                                    cardNumber = ""
                                    cardName = ""
                                    expiryMonth = ""
                                    expiryYear = ""
                                    cvv = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonBlue
                            ),
                            enabled = !isProcessing && !processingComplete,
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
                            } else if (processingComplete) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Success",
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "FUNDS ADDED",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else {
                                Text(
                                    text = "CONFIRM PAYMENT",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        if (!isProcessing && !processingComplete) {
                            TextButton(
                                onClick = { currentScreen = "card" },
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

            // Security notice
            AnimatedVisibility(
                visible = !isProcessing,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Secure",
                        tint = secondaryTextColor,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Secured by ChainEx Blockchain Technology",
                        fontSize = 12.sp,
                        color = secondaryTextColor
                    )
                }
            }
        }

        // Success animation overlay - moved outside the Column to overlay the entire screen
        AnimatedVisibility(
            visible = processingComplete,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(accentGreen.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = accentGreen,
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Funds Added Successfully!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${formatForeign.format(amountValue)} → ${formatInr.format(finalAmount)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = accentGreen
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Added to your ChainEx wallet",
                        fontSize = 14.sp,
                        color = secondaryTextColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Transaction ID: ${generateTransactionId()}",
                        fontSize = 12.sp,
                        color = secondaryTextColor
                    )
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
        modifier = Modifier.fillMaxWidth(),
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

// Helper function to generate a formatted current time
private fun getCurrentTimeFormatted(): String {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH) + 1 // Month is 0-based
    val year = calendar.get(Calendar.YEAR)

    return String.format(
        "%02d:%02d, %02d/%02d/%d",
        hour,
        minute,
        day,
        month,
        year
    )
}

// Helper function to generate a random transaction ID
private fun generateTransactionId(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    val prefix = "TX"
    val random = java.util.Random()
    val suffix = (1..8)
        .map { chars[random.nextInt(chars.length)] }
        .joinToString("")

    return "$prefix-$suffix"
}

@Preview(showBackground = true)
@Composable
fun AddFundsScreenPreview() {
    AddFundsScreen()
}