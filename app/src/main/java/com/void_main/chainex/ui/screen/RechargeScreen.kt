package com.void_main.chainex.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.void_main.chainex.R
import com.void_main.chainex.ui.theme.ChainEXTheme
import com.void_main.chainex.ui.theme.Grey_color

@Composable
fun RechargeScreen() {

    Scaffold(
        floatingActionButton = {
            Icon(imageVector = Icons.Default.ArrowForward,null)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            Image(
                painter = painterResource(id = R.drawable.recharge_screen),
                null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(shape = RectangleShape),
                colors = ButtonColors(
                    containerColor = Grey_color,
                    contentColor = Color.White,
                    disabledContainerColor = Grey_color,
                    disabledContentColor = Grey_color
                )
            ) {
                Text(
                    "SetUp Now", modifier = Modifier.padding(vertical = 8.dp), fontSize = 16.sp
                )
            }

            Text(
                "Learn how to",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(id = R.drawable.scan_n_pay_recharge_screen),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.clickable {

                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.pay_online_recharege_screen),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.clickable {

                    }
                )

            }

        }

    }



}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRechargeScreen() {
    ChainEXTheme {
//        RechargeScreen(navController)
    }

}
