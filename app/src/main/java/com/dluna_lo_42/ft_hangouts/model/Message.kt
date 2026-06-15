package com.dluna_lo_42.ft_hangouts.model

data class Message(
    val id: Long = -1,
    val contactId: Long,
    val content: String,
    val timestamp: Long,
    val isSent: Boolean
)
