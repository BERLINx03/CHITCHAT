package com.berlin.chit_chat.home.presentation

import androidx.lifecycle.ViewModel
import com.berlin.chit_chat.chat.domain.model.Channel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author Abdallah Elsokkary
 */
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val firebaseDatabase = Firebase.database

    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels: StateFlow<List<Channel>> = _channels.asStateFlow()

    init {
        fetchChannels()
    }


    private fun fetchChannels() {
        firebaseDatabase.getReference("channel").get().addOnSuccessListener {
            val list = mutableListOf<Channel>()
            it.children.forEach { data ->
                val channel = Channel(data.key!!,data.value.toString())
                list.add(channel)
            }
            _channels.value = list
        }
    }

    fun addChannel(channelName: String) {
        val key = firebaseDatabase.getReference("channel").push().key
        firebaseDatabase.getReference("channel")
            .child(key!!).setValue(channelName)
            .addOnSuccessListener {
                fetchChannels()
            }
    }
}