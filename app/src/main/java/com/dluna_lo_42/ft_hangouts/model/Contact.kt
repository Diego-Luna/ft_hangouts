package com.dluna_lo_42.ft_hangouts.model

data class Contact(
    val id: Long = -1,
    val name: String,
    val phoneNumber: String,
    val email: String? = null,
    val address: String? = null,
    val company: String? = null,
    val pictureUri: String? = null
)
