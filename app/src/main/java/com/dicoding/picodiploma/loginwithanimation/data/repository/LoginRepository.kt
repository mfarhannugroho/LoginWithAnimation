package com.dicoding.picodiploma.loginwithanimation.data.repository

import com.dicoding.picodiploma.loginwithanimation.data.network.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.response.RegisterResponse

class LoginRepository (private val apiService: ApiService) {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }
}