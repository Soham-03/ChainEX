package com.void_main.chainex.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillScreen(
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
    val expenseRed = Color(0xFFE53935)

    // Format currency
    val formatInr = NumberFormat.getCurrencyInstance(Locale("en", "IN"))

    // Income and Expenses
    val usdToInrRate = 82.5f
    val totalIncome = 200f * usdToInrRate // $200 converted to INR
    val totalExpenses = 0f // No expenses yet
    val balance = 15801

    // Expense categories
    val expenseCategories = listOf(
        ExpenseCategory("Food & Dining", Icons.Default.Restaurant, 0f, Color(0xFFFF6B6B)),
        ExpenseCategory("Shopping", Icons.Default.ShoppingCart, 0f, Color(0xFF4ECDC4)),
        ExpenseCategory("Entertainment", Icons.Default.Movie, 0f, Color(0xFF45B7D1)),
        ExpenseCategory("Transport", Icons.Default.DirectionsCar, 0f, Color(0xFF96CEB4)),
        ExpenseCategory("Bills & Utilities", Icons.Default.Receipt, 0f, Color(0xFFFFBE0B)),
        ExpenseCategory("Health", Icons.Default.LocalHospital, 0f, Color(0xFFF72585)),
        ExpenseCategory("Education", Icons.Default.School, 0f, Color(0xFF7209B7)),
        ExpenseCategory("Others", Icons.Default.MoreHoriz, 0f, Color(0xFF6C757D))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Bills & Expenses",
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
            // Summary Card
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
                        text = "Financial Summary",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Balance
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Current Balance",
                            fontSize = 16.sp,
                            color = secondaryTextColor
                        )
                        Text(
                            text = formatInr.format(balance),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = accentGreen
                        )
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = borderGray
                    )

                    // Income
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Income",
                            fontSize = 16.sp,
                            color = secondaryTextColor
                        )
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = formatInr.format(balance),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = accentGreen
                            )
                            Text(
                                text = "($200 payment)",
                                fontSize = 12.sp,
                                color = secondaryTextColor
                            )
                        }
                    }

                    // Expenses
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Expenses",
                            fontSize = 16.sp,
                            color = secondaryTextColor
                        )
                        Text(
                            text = formatInr.format(totalExpenses),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = expenseRed
                        )
                    }
                }
            }

            // Visual Chart Card
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
                        text = "Income vs Expenses",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Simple Bar Chart
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        // Income Bar
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(180.dp)
                                    .background(
                                        color = accentGreen,
                                        shape = RoundedCornerShape(
                                            topStart = 8.dp,
                                            topEnd = 8.dp
                                        )
                                    )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Income",
                                fontSize = 14.sp,
                                color = textColor
                            )
                            Text(
                                text = formatInr.format(totalIncome),
                                fontSize = 12.sp,
                                color = secondaryTextColor,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Expenses Bar
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(20.dp) // Small height since expenses are 0
                                    .background(
                                        color = expenseRed,
                                        shape = RoundedCornerShape(
                                            topStart = 8.dp,
                                            topEnd = 8.dp
                                        )
                                    )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Expenses",
                                fontSize = 14.sp,
                                color = textColor
                            )
                            Text(
                                text = formatInr.format(totalExpenses),
                                fontSize = 12.sp,
                                color = secondaryTextColor,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Expense Categories Section
            Text(
                text = "Expense Categories",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
            )

            // Category Cards
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                expenseCategories.forEach { category ->
                    ExpenseCategoryCard(
                        category = category,
                        onCategoryClick = { /* Handle category click */ },
                        formatInr = formatInr,
                        textColor = textColor,
                        secondaryTextColor = secondaryTextColor,
                        borderGray = borderGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Add Expense Button
            Button(
                onClick = { /* Handle add expense */ },
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
                    text = "Add New Expense",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

data class ExpenseCategory(
    val name: String,
    val icon: ImageVector,
    val amount: Float,
    val color: Color
)

@Composable
fun ExpenseCategoryCard(
    category: ExpenseCategory,
    onCategoryClick: () -> Unit,
    formatInr: NumberFormat,
    textColor: Color,
    secondaryTextColor: Color,
    borderGray: Color
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCategoryClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(category.color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = null,
                    tint = category.color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Category Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
                Text(
                    text = if (category.amount == 0f) "No expenses yet" else "Spent this month",
                    fontSize = 14.sp,
                    color = secondaryTextColor
                )
            }

            // Amount
            Text(
                text = formatInr.format(category.amount),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (category.amount > 0) Color(0xFFE53935) else secondaryTextColor
            )
        }
    }
}

@Composable
fun PreviewBillScreen() {
    BillScreen()
}