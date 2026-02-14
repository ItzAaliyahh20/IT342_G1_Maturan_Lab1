package com.example.authappmobile.network

import com.example.authappmobile.model.AuthResponse
import com.example.authappmobile.model.LoginRequest
import com.example.authappmobile.model.RegisterRequest
import com.example.authappmobile.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    suspend fun login(@Body req: LoginRequest): Response<AuthResponse>

    @POST("/register")
    suspend fun register(@Body req: RegisterRequest): Response<AuthResponse>

    @GET("/profile")
    suspend fun profile(@Header("Authorization") authHeader: String): Response<User>
}
