package com.example.authappmobile.model

data class AuthResponse(
    val token: String,
    val userId: Long,
    val name: String,
    val email: String
)
