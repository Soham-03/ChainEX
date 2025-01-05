package com.example.fiintechapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.void_main.chainex.R
import com.void_main.chainex.Transaction
import com.void_main.chainex.TransactionStatus
import com.void_main.chainex.Utils
import com.void_main.chainex.ui.theme.Button_color
import com.void_main.chainex.ui.theme.Green
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen() {
//    var transactions = remember {
//        mutableStateListOf(
//            Transaction(1000, "Rahul Sharma", "0x1234...5678", TransactionStatus.SUCCESS),
//            Transaction(500, "Priya Patel", "0x8765...4321", TransactionStatus.PENDING),
//            Transaction(750, "Amit Kumar", "0x9876...5432", TransactionStatus.FAILED),
//            Transaction(2000, "Deepika Singh", "0x3456...7890", TransactionStatus.SUCCESS),
//            Transaction(1500, "Rajesh Verma", "0x6543...2109", TransactionStatus.SUCCESS),
//            Transaction(300, "Anjali Gupta", "0x2345...6789", TransactionStatus.PENDING),
//            Transaction(900, "Vikas Malhotra", "0x7654...3210", TransactionStatus.SUCCESS),
//            Transaction(1200, "Neha Reddy", "0x4567...8901", TransactionStatus.FAILED),
//            Transaction(600, "Suresh Iyer", "0x8901...2345", TransactionStatus.SUCCESS),
//            Transaction(800, "Meera Krishnan", "0x5678...9012", TransactionStatus.SUCCESS)
//        )
//    }
    var transactions = Utils.transactions
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
    ) {
        // Black header section matching HomeScreen
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 12.dp, end = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Payment History",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .size(30.dp)
                )
            }
            Text(
                text = "View all your transactions",
                fontSize = 14.sp,
                color = Green,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }


        // Transaction List
        LazyColumn(
            modifier = Modifier.padding(top = 16.dp),
            reverseLayout = true
        ) {
            item{
                Spacer(Modifier.height(100.dp))
            }
            items(transactions) { transaction ->
                TransactionCard(transaction)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

//@Composable
//fun TransactionCard(transaction: Transaction) {
//    val gradientColor = when (transaction.status) {
//        TransactionStatus.SUCCESS -> Green
//        TransactionStatus.PENDING -> Color.Black
//        TransactionStatus.FAILED -> Color.Red
//    }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color.White
//        ),
//        shape = RoundedCornerShape(20.dp),
//        border = BorderStroke(1.dp, Button_color)
//    ) {
//        Box(
//            modifier = Modifier
//                .background(
//                    brush = Brush.horizontalGradient(
//                        colors = listOf(
//                            gradientColor.copy(alpha = 0.1f),
//                            Color.White
//                        ),
//                        startX = 0f,
//                        endX = 900f
//                    )
//                )
//                .fillMaxWidth()
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp)
//                    .background(Color.Transparent)
//            ) {
//                Text(
//                    text = "₹${transaction.amount}",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(
//                    text = "To: ${transaction.receiver}",
//                    fontSize = 16.sp,
//                    color = Color.Black.copy(alpha = 0.8f)
//                )
//
//                Spacer(modifier = Modifier.height(4.dp))
//
//                Text(
//                    text = transaction.txHash,
//                    fontSize = 14.sp,
//                    color = Color.Black.copy(alpha = 0.6f)
//                )
//
//                Text(
//                    text = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
//                        .format(Date(transaction.timestamp)),
//                    fontSize = 12.sp,
//                    color = Color.Black.copy(alpha = 0.5f)
//                )
//            }
//        }
//    }
//}

@Composable
fun TransactionCard(transaction: Transaction) {
    val (gradientColor, icon) = when (transaction.status) {
        TransactionStatus.SUCCESS -> Pair(
            Green,
            painterResource(id = R.drawable.success_check) // Add check icon to drawables
        )
        TransactionStatus.PENDING -> Pair(
            Color.Black,
            painterResource(id = R.drawable.pending_clock) // Add clock icon to drawables
        )
        TransactionStatus.FAILED -> Pair(
            Color.Red,
            painterResource(id = R.drawable.failed_cross) // Add cross icon to drawables
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Button_color)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            gradientColor.copy(alpha = 0.1f),
                            Color.White
                        ),
                        startX = 0f,
                        endX = 900f
                    )
                )
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Transparent),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side: Icon and Transaction Details
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Status Icon
                    // Transaction Details
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "₹${transaction.amount}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = transaction.status.name,
                                fontSize = 12.sp,
                                color = gradientColor,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "To: ${transaction.receiver}",
                            fontSize = 16.sp,
                            color = Color.Black.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Medium
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.blockchain_icon), // Add blockchain icon
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = transaction.txHash,
                                fontSize = 14.sp,
                                color = Color.Black.copy(alpha = 0.6f)
                            )
                        }

                        Text(
                            text = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                                .format(Date(transaction.timestamp)),
                            fontSize = 12.sp,
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                    }

                    Image(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 12.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun PaymentStatusChip(status: TransactionStatus) {
    val (backgroundColor, textColor) = when (status) {
        TransactionStatus.SUCCESS -> Green to Color.White
        TransactionStatus.PENDING -> Color.Black to Color.White
        TransactionStatus.FAILED -> Color.Red to Color.White
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 12.sp,
            color = textColor
        )
    }
}

