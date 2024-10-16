package com.example.fiintechapp.ui.screen

import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.void_main.chainex.R
import com.void_main.chainex.ui.composables.SpinWheelCard
import com.void_main.chainex.ui.theme.Grey_color
import com.void_main.chainex.ui.theme.Reward_Button_color

@Composable
fun RewardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Grey_color)
                .height(200.dp)
                .padding(16.dp, 0.dp), verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                "Rewards", fontWeight = FontWeight.W600, fontSize = 30.sp, color = Color.White
            )
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(0.4f)
                        .height(50.dp),
                    colors = ButtonColors(
                        Reward_Button_color,
                        Color.White,
                        Color.White,
                        Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(R.drawable.rupee),
                            contentDescription = null,
                        )
                        Spacer(Modifier.width(16.dp))
                        Text("0", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                    }

                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(0.4f)
                        .height(50.dp),
                    colors = ButtonColors(
                        Reward_Button_color,
                        Color.White,
                        Color.White,
                        Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(R.drawable.bitcoin__1_),
                            contentDescription = null,
                        )
                        Spacer(Modifier.width(16.dp))
                        Text("0", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                    }

                }
            }
        }

        SpinWheelCard()


        Text(
            "Earn as you spend",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp),
            color = Grey_color,
            textAlign = TextAlign.Center
        )



        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .height(850.dp),
            contentPadding = PaddingValues(8.dp, 16.dp)
        ) {
            items(5) { index ->
                Image(
                    painter = painterResource(
                        when (index) {
                            0 -> R.drawable.first_card
                            1 -> R.drawable.second_card
                            2 -> R.drawable.third_card
                            3 -> R.drawable.fourth_card
                            else -> R.drawable.fifth_card
                        }
                    ),
                    contentDescription = "Card ${index + 1}",
                    modifier = Modifier
                        .padding(8.dp, 16.dp)
                        .clickable {

                            when (index) {
                                0 -> {}
                                1 -> {}
                                2 -> {}
                                3 -> {}
                                else -> {}
                            }
                        },
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewRewardScreen() {
    RewardScreen()
}
