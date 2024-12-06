package com.berlin.chit_chat.chat.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.berlin.chit_chat.R
import com.berlin.chit_chat.chat.domain.model.Channel

/**
 * @author Abdallah Elsokkary
 */
@Composable
fun ChannelItem(
    modifier: Modifier = Modifier,
    channel: Channel,
    navController: NavController
) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("chat/${channel.id}")
                }
                .padding(10.dp)
        ){
            Image(
                painter = painterResource(R.drawable.chitchatlogo),
                contentDescription = stringResource(R.string.user_image, channel.name),
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = channel.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        Divider()
}

@Preview(showBackground = true)
@Composable
fun ChannelCardPreview() {
    ChannelItem(
        navController = rememberNavController(),
        channel = Channel(
            id = "1",
            name = "General"
        )
    )
}