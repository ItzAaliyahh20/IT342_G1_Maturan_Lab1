package com.example.authappmobile.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val token: String,
    @SerializedName("userId")
    val userId: Long,
    val name: String,
    val email: String
)
