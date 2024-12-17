// LoginViewModel.kt
package com.dicoding.picodiploma.loginwithanimation.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loggedInUser = MutableLiveData<UserModel?>()
    val loggedInUser: LiveData<UserModel?> = _loggedInUser

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
            Log.d("LoginViewModel", "Token berhasil disimpan: ${user.token}")
        }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.login(email, password)
                if (result.error == false) {
                    _isLogin.value = true
                    val loginResult = result.loginResult
                    if (loginResult != null) {
                        val user = loginResult.token?.let { UserModel(email, it, true) }
                        _loggedInUser.postValue(user)
                        if (user != null) {
                            saveSession(user)
                            repository.saveToken(loginResult.token)
                            Log.d("LoginViewModel", "Token saved: ${loginResult.token}")
                        }
                    }
                } else {
                    _isLogin.value = false
                    Log.e("LoginViewModel", "Login failed: ${result.message}")
                }
            } catch (e: Exception) {
                _isLogin.value = false
                Log.e("LoginViewModel", "Login failed with exception: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}