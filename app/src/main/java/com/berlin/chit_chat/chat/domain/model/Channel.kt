package com.berlin.chit_chat.chat.domain.model


data class Channel(
    val id: String,
    val name: String,
    val createdAt: Long = System.currentTimeMillis(),
    val description: String? = null,
    val members: List<String> = emptyList()
)

