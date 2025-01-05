package com.example.fiintechapp.ui.composables

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.void_main.chainex.R
import com.void_main.chainex.activity.ItenaryActivity
import com.void_main.chainex.ui.theme.Grey_color

//it is dummy will add data after wards


@Composable
fun ReviewDiscoverCardItem() {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .width(350.dp)
            .padding(16.dp)
            .clickable {

            },
        shape = RoundedCornerShape(16.dp),
//        modifier = Modifier

//        elevation = 8.dp,
    ) {
        Column(modifier = Modifier.background(Grey_color).clickable {
            val intent = Intent(context, ItenaryActivity::class.java)
            context.startActivity(intent)
        }) {
            // Image section
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your image URL
                contentDescription = "Market Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            // Text section
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.man), // Replace with an actual image or drawable
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(50)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Name and Location
                    Text(
                        text = "Renatha, Brazil", style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "The market tour I booked was a delightful immersion into the heart of Jaipur. " + "Led by a knowledgeable guide, it was a culinary adventure filled with exotic flavors, vibrant colors, " + "and rich cultural experiences. Highly recommend!",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.LightGray, fontSize = 14.sp
                    )
                )
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewReviewDiscoverCardItem() {

}