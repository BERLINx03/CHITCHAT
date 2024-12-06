package com.berlin.chit_chat.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.berlin.chit_chat.R
import com.berlin.chit_chat.chat.presentation.ChannelItem

/**
 * @author Abdallah Elsokkary
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier,navController: NavController) {
    val vm = hiltViewModel<HomeViewModel>()
    val channels by vm.channels.collectAsState()
    var addChannelDialog by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()

    Scaffold (
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .clickable {
                        addChannelDialog = true
                    }
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_chat),
                    modifier = Modifier.size(45.dp).padding(10.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ){ paddingValues ->
        Box(modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            LazyColumn {
                item {
                    Divider()
                }
                items(channels){
                    ChannelItem(channel = it, navController = navController)
                }
            }
        }
    }

    if (addChannelDialog){
        ModalBottomSheet(
            onDismissRequest = { addChannelDialog = false},
            sheetState = sheetState
        ) {
            AddChannelDialog {
                vm.addChannel(it)
                addChannelDialog = false
            }
        }
    }
}