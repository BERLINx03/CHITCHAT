package com.berlin.chit_chat.chat.presentation

import androidx.lifecycle.ViewModel
import com.berlin.chit_chat.chat.domain.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

/**
 * @author Abdallah Elsokkary
 */

/**
 * rethink about this file
 * */
@HiltViewModel
class ChatViewModel @Inject constructor(): ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    private val database = Firebase.database


    fun sendMessage(channelId: String, messageText: String){
        val message = Message(
            id = database.reference.push().key ?: UUID.randomUUID().toString(),
            senderId = Firebase.auth.currentUser!!.uid,
            message = messageText,
            createdAt = System.currentTimeMillis(),
            senderName = Firebase.auth.currentUser!!.displayName!!,
            senderImage = Firebase.auth.currentUser!!.photoUrl.toString(),
            imageUrl = null
        )

        database.reference.child("messages").child(channelId).push().setValue(message)
    }

    fun fetchListenMessages(channelId: String){
        database.getReference("messages").child(channelId).orderByChild("createdAt")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Message>()
                    snapshot.children.forEach {
                        val message = it.getValue(Message::class.java)
                        list.add(message!!)
                    }
                    _messages.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}
























