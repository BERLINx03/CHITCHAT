package com.berlin.chit_chat.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.berlin.chit_chat.R
import kotlin.math.sin

/**
 * @author Abdallah Elsokkary
 */
@Composable
fun AddChannelDialog(
    modifier: Modifier = Modifier,
    onAddNewChannel: (String) -> Unit
) {
    var channelName by remember {
        mutableStateOf("")
    }

    Column (
        modifier = modifier.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = stringResource(R.string.add_new_channel))
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = channelName,
            onValueChange = {
                channelName = it
            },
            label = {
                Text(text = stringResource(R.string.channel_name))
            },
            placeholder = {
                Text(text = stringResource(R.string.channel_name))
            },
            singleLine = true,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick = { onAddNewChannel(channelName) },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            enabled = channelName.isNotBlank()
        ) {
            Text(text = stringResource(id = R.string.add_channel))
        }
    }
}