package com.void_main.chainex.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.void_main.chainex.Transaction
import com.void_main.chainex.TransactionStatus
import com.void_main.chainex.Utils
import java.text.SimpleDateFormat
import java.util.*

enum class SortOption {
    DATE_NEWEST, DATE_OLDEST, AMOUNT_HIGH, AMOUNT_LOW, STATUS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen() {
    // State management
    val transactions = remember { Utils.transactions }
    var showSortMenu by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedSortOption by remember { mutableStateOf(SortOption.DATE_NEWEST) }
    var selectedDate by remember { mutableStateOf<Date?>(null) }

    // Colors
    val lightBackground = Color(0xFFF8F9FA)
    val accentGreen = Color(0xFF4CAF50)
    val buttonBlue = Color(0xFF1A73E8)
    val textColor = Color(0xFF202124)
    val secondaryTextColor = Color(0xFF5F6368)

    // Date picker state
    val datePickerState = rememberDatePickerState()

    // Sort and filter transactions
    val sortedTransactions = remember(transactions, selectedSortOption, selectedDate) {
        try {
            var filtered = if (selectedDate != null) {
                transactions.filter {
                    val transactionDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it.timestamp))
                    val selectedDateString = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(selectedDate!!)
                    transactionDate == selectedDateString
                }
            } else {
                transactions
            }

            when (selectedSortOption) {
                SortOption.DATE_NEWEST -> filtered.sortedByDescending { it.timestamp }
                SortOption.DATE_OLDEST -> filtered.sortedBy { it.timestamp }
                SortOption.AMOUNT_HIGH -> filtered.sortedByDescending { it.amount }
                SortOption.AMOUNT_LOW -> filtered.sortedBy { it.amount }
                SortOption.STATUS -> filtered.sortedBy { it.status.ordinal }
            }
        } catch (e: Exception) {
            transactions
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Payment History",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "View all your transactions",
                            fontSize = 14.sp,
                            color = accentGreen
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Refresh */ }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(lightBackground)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Filter and Sort Controls
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Date Filter Button
                    OutlinedButton(
                        onClick = { showDatePicker = true },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (selectedDate != null) buttonBlue.copy(alpha = 0.1f) else Color.Transparent
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (selectedDate != null) buttonBlue else Color(0xFFE0E0E0)
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = if (selectedDate != null) buttonBlue else secondaryTextColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = selectedDate?.let {
                                SimpleDateFormat("dd MMM", Locale.getDefault()).format(it)
                            } ?: "Date",
                            color = if (selectedDate != null) buttonBlue else textColor
                        )
                        if (selectedDate != null) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear date",
                                tint = buttonBlue,
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { selectedDate = null }
                            )
                        }
                    }

                    // Sort Button
                    OutlinedButton(
                        onClick = { showSortMenu = true },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (selectedSortOption != SortOption.DATE_NEWEST) buttonBlue.copy(alpha = 0.1f) else Color.Transparent
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (selectedSortOption != SortOption.DATE_NEWEST) buttonBlue else Color(0xFFE0E0E0)
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = null,
                            tint = if (selectedSortOption != SortOption.DATE_NEWEST) buttonBlue else secondaryTextColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (selectedSortOption) {
                                SortOption.DATE_NEWEST -> "Newest"
                                SortOption.DATE_OLDEST -> "Oldest"
                                SortOption.AMOUNT_HIGH -> "High Amount"
                                SortOption.AMOUNT_LOW -> "Low Amount"
                                SortOption.STATUS -> "By Status"
                            },
                            color = if (selectedSortOption != SortOption.DATE_NEWEST) buttonBlue else textColor
                        )
                    }
                }

                // Transaction Stats
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TransactionStatItem(
                            title = "Total",
                            value = sortedTransactions.size.toString(),
                            color = buttonBlue
                        )
                        TransactionStatItem(
                            title = "Success",
                            value = sortedTransactions.count { it.status == TransactionStatus.SUCCESS }.toString(),
                            color = accentGreen
                        )
                        TransactionStatItem(
                            title = "Failed",
                            value = sortedTransactions.count { it.status == TransactionStatus.FAILED }.toString(),
                            color = Color(0xFFE53935)
                        )
                        TransactionStatItem(
                            title = "Pending",
                            value = sortedTransactions.count { it.status == TransactionStatus.PENDING }.toString(),
                            color = Color(0xFFFFA000)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Transaction List
                if (sortedTransactions.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.SearchOff,
                                contentDescription = null,
                                tint = secondaryTextColor,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No transactions found",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = textColor
                            )
                            if (selectedDate != null) {
                                Text(
                                    text = "Try clearing the date filter",
                                    fontSize = 14.sp,
                                    color = secondaryTextColor,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        itemsIndexed(
                            items = sortedTransactions,
                            key = { index, transaction ->
                                "${transaction.timestamp}_${index}"
                            }
                        ) { index, transaction ->
                            TransactionCard(transaction = transaction)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

            // Sort Menu Dropdown
            DropdownMenu(
                expanded = showSortMenu,
                onDismissRequest = { showSortMenu = false },
                modifier = Modifier.background(Color.White)
            ) {
                SortOption.values().forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedSortOption == option,
                                    onClick = null,
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = buttonBlue
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = when (option) {
                                        SortOption.DATE_NEWEST -> "Newest First"
                                        SortOption.DATE_OLDEST -> "Oldest First"
                                        SortOption.AMOUNT_HIGH -> "Highest Amount"
                                        SortOption.AMOUNT_LOW -> "Lowest Amount"
                                        SortOption.STATUS -> "By Status"
                                    }
                                )
                            }
                        },
                        onClick = {
                            selectedSortOption = option
                            showSortMenu = false
                        }
                    )
                }
            }
        }

        // Date Picker Dialog
        if (showDatePicker) {
            DatePickerDialog(
                modifier = Modifier.padding(8.dp),
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                selectedDate = Date(millis)
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Composable
fun TransactionStatItem(
    title: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color(0xFF5F6368)
        )
    }
}

@Composable
fun TransactionCard(transaction: Transaction) {
    val (gradientColor, statusIcon) = when (transaction.status) {
        TransactionStatus.SUCCESS -> Pair(
            Color(0xFF4CAF50),
            Icons.Default.CheckCircle
        )
        TransactionStatus.PENDING -> Pair(
            Color(0xFFFFA000),
            Icons.Default.HourglassEmpty
        )
        TransactionStatus.FAILED -> Pair(
            Color(0xFFE53935),
            Icons.Default.Cancel
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            gradientColor.copy(alpha = 0.1f),
                            Color.White
                        )
                    )
                )
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "₹${transaction.amount}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF202124)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = gradientColor.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = transaction.status.name,
                                fontSize = 12.sp,
                                color = gradientColor,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Text(
                        text = "To: ${transaction.receiver}",
                        fontSize = 16.sp,
                        color = Color(0xFF202124).copy(alpha = 0.8f),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = Color(0xFF5F6368)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = transaction.txHash,
                            fontSize = 14.sp,
                            color = Color(0xFF5F6368)
                        )
                    }

                    Text(
                        text = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                            .format(Date(transaction.timestamp)),
                        fontSize = 12.sp,
                        color = Color(0xFF5F6368),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(gradientColor.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = statusIcon,
                        contentDescription = null,
                        tint = gradientColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}