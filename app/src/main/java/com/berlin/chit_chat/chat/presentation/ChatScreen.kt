package com.berlin.chit_chat.chat.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.berlin.chit_chat.chat.domain.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

/**
 * @author Abdallah Elsokkary
 */
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    channelId: String
) {
    val vm = hiltViewModel<ChatViewModel>()

    val messages by vm.messages.collectAsState()

    LaunchedEffect(true) {
        vm.fetchListenMessages(channelId)
    }

    Scaffold {
        ChatMessages(
            paddingValues = it,
            message = messages,
            onSendMessage = {
                vm.sendMessage(channelId, it)
            }
        )
    }
}


@Composable
fun ChatMessages(
    paddingValues: PaddingValues,
    message: List<Message>,
    onSendMessage: (String) -> Unit
) {
    val hideKeyboardController = LocalSoftwareKeyboardController.current
    var msg by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        LazyColumn {
            items(message) {
                MessageItem(message = it)
            }
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ){
            TextField(
                value = msg,
                onValueChange = { msg = it },
                placeholder = {
                    Text(text = "Type your message")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
            )
            IconButton(onClick = {onSendMessage(msg);msg = ""}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send Message"
                )
            }
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    val isCurrentUser = message.senderId == Firebase.auth.currentUser!!.uid
    val alignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
    val messageColor = if (isCurrentUser) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.secondary
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = messageColor,
                    shape = MaterialTheme.shapes.medium,
                )
                .align(alignment)
        ) {
            Text(
                text = message.message,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
