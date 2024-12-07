package com.dicoding.picodiploma.loginwithanimation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.loginwithanimation.model.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.model.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = liveData(Dispatchers.IO) {
        try {
            val response = userRepository.register(name, email, password)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure<RegisterResponse>(e))
        }
    }

    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        try {
            val response = userRepository.login(email, password)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure<LoginResponse>(e))
        }
    }
}