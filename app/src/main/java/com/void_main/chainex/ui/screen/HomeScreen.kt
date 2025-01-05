package com.example.fiintechapp.ui.screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.void_main.chainex.AddFundsActivity
import com.void_main.chainex.PaymentActivity
import com.void_main.chainex.R
import com.void_main.chainex.ui.composables.IconButtonWithLabel
import com.void_main.chainex.ui.theme.Button_color
import com.void_main.chainex.ui.theme.Green
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val barCodeScanner = com.void_main.chainex.util.BarcodeScanner(context)
    Column(
        Modifier
            .imePadding()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 12.dp)
            ){
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp, end = 12.dp)
                        .align(Alignment.End)
                ){
                    Icon(
                        imageVector =  Icons.Filled.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .size(40.dp)
                    )
                }
                Text(
                    text = "VERFIY YOUR SIM",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Column {
                        Text(
                            text = "To activate your\nUPI Payments",
                            fontSize = 24.sp,
                            color = Green,
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                        )
                        Button (
                            onClick = {}, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                        ),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.5.dp, Green)
                        ) {
                            Text("VERIFY SIM")
                        }
                    }

                    Image(
                        painter = painterResource(R.drawable.sim_card),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                    )

                }

            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(10.dp, 12.dp),
                border = BorderStroke(1.dp, Button_color),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(30.dp)
                )
                Text(
                    text = "Search name, UPI ID or Number",
                    fontSize = 18.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                val scope = rememberCoroutineScope()
                val barcodeRes = barCodeScanner.barCodeRes.collectAsStateWithLifecycle()
                var QROP = ""
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Button(
                        shape = CircleShape,
                        onClick = {
                            scope.launch {
                                barCodeScanner.startScan()
                                barcodeRes.value.toString()
                            }
                        },
                        border = BorderStroke(2.dp, Color.Black),
                        colors = ButtonDefaults
                            .buttonColors(
                                containerColor = Color.White
                            ),
                        modifier = Modifier
                            .size(64.dp)

                    ) {
                        Image(
                            painter = painterResource(R.drawable.scan_qr_code),
                            null,
                            modifier = Modifier
                                .size(100.dp)
                        )
                    }
                    Text("Scan QR")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    Button(
                        shape = CircleShape,
                        onClick = {
                            val intent = Intent(context, AddFundsActivity::class.java)
                            context.startActivity(intent)
                        },
                        border = BorderStroke(2.dp, Color.Black),
                        colors = ButtonDefaults
                            .buttonColors(
                                containerColor = Color.White
                            ),
                        modifier = Modifier
                            .size(64.dp)

                    ) {
                        Image(
                            painter = painterResource(R.drawable.wallet),
                            null,
                            modifier = Modifier
                                .size(100.dp)
                        )
                    }
                    Text("Add Funds")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Button(
                        shape = CircleShape,
                        onClick = {
                            val intent = Intent( context, PaymentActivity::class.java)
                            context.startActivity(intent)
                        },
                        border = BorderStroke(2.dp, Color.Black),
                        colors = ButtonDefaults
                            .buttonColors(
                                containerColor = Color.White
                            ),
                        modifier = Modifier
                            .size(64.dp)

                    ) {
                        Image(
                            painter = painterResource(R.drawable.receipt_text),
                            null,
                            modifier = Modifier
                                .size(100.dp)
                        )
                    }
                    Text("Bills")
                }
            }


            Button(
                onClick = { },
                modifier = Modifier
                    .height(70.dp)
                    .padding(16.dp, 12.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                border = BorderStroke(1.dp, Button_color),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Icon(
                    painter = painterResource(R.drawable.wallet),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(16.dp)
                )
                Text(
                    text = "Personal Wallet",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.invite_friend_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .padding(16.dp, 0.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        //invite friend activity
                    },
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(100.dp))
        }


    }

}


@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen()
}
