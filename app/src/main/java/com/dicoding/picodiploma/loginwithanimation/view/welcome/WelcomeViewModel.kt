package com.dicoding.picodiploma.loginwithanimation.view.welcome

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference

class WelcomeViewModel(private val userPreference: UserPreference) : ViewModel() {
    fun getSession() = userPreference.getSession()
}