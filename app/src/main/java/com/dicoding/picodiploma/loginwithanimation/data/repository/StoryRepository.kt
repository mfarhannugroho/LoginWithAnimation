package com.dicoding.picodiploma.loginwithanimation.data.repository

import com.dicoding.picodiploma.loginwithanimation.data.network.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference

class StoryRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, userPreference)
            }.also { instance = it }
    }
}