package com.example.authappmobile.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val user_id: Long,
    val email: String,
    @SerializedName("first_name")
    val first_name: String,
    @SerializedName("last_name")
    val last_name: String
)
