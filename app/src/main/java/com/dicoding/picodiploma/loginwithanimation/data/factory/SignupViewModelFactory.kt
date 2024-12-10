package com.dicoding.picodiploma.loginwithanimation.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.loginwithanimation.data.repository.UserRepository
import com.dicoding.picodiploma.loginwithanimation.view.signup.SignupViewModel

class SignupViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignupViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}