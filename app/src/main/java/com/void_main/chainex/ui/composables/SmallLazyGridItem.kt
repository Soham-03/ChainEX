package com.example.fintech_app.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.void_main.chainex.R

@Composable
fun SmallLazyGridItem(){
    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(100.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Cafe")
            Image(
                painter = painterResource(id = R.drawable.cafe),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}