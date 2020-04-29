package com.techfort.planetapi.remot.model

class UserResponse : ArrayList<UserResponseItem>()

data class UserResponseItem(
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val password: String
)