package com.dicoding.picodiploma.loginwithanimation.repository

import ApiService
import com.dicoding.picodiploma.loginwithanimation.model.ErrorResponse
import com.dicoding.picodiploma.loginwithanimation.model.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.model.RegisterResponse
import com.google.gson.Gson
import retrofit2.HttpException

class UserRepository(private val apiService: ApiService) {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return try {
            apiService.register(name, email, password)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            throw Exception(errorBody.message)
        }
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return try {
            apiService.login(email, password)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            throw Exception(errorBody.message)
        }
    }
}