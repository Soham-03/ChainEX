package com.void_main.chainex.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.void_main.chainex.R
import com.void_main.chainex.ui.theme.Button_Color_RewardScreen
import com.void_main.chainex.ui.theme.Coloum_Bg_Color_RewardScreen

@Composable
fun SpinWheelCard() {
    Card (
        modifier = Modifier.height(250.dp).padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Coloum_Bg_Color_RewardScreen
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Spin the wheel",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.W600,
                    color = Button_Color_RewardScreen
                )

                Text(
                    text = "Get assured cashback \nby using gems!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    color = Button_Color_RewardScreen
                )

                Button(
                    onClick = {},
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = Button_Color_RewardScreen,
                        disabledContainerColor = Button_Color_RewardScreen,
                        disabledContentColor = Color.White
                    )
                ) {
                    Text("Play now")
                }
            }

            Image(
                painter = painterResource(R.drawable.spin_wheel),
                null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.weight(1f).align(Alignment.Bottom)
            )
        }


    }

}


//Card(
//elevation = 4.dp,
//shape = RoundedCornerShape(8.dp),
//backgroundColor = Color.White
//) {
//    Column(
//        modifier = Modifier.padding(16.dp)
//    ) {
//        Text(
//            text = "Spin the wheel",
//            style = MaterialTheme.typography.h6
//        )
//        Text(
//            text = "Get assured cashback by using gems!",
//            style = MaterialTheme.typography.body1
//        )
//        Image(
//            painter = painterResource(R.drawable.wheel_image),
//            contentDescription = "Cashback wheel",
//            modifier = Modifier.size(200.dp)
//        )
//        Button(
//            onClick = { /* Handle button click */ },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = "Play now"
//            )
//        }
//        // Add code for displaying cashback amounts
//    }
//}