package com.dicoding.picodiploma.loginwithanimation.data.repository

import com.dicoding.picodiploma.loginwithanimation.data.network.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference

class StoryIDRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun getStoryDetail(id: String) = apiService.getStoryDetail(id)

    companion object {
        @Volatile
        private var instance: StoryIDRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): StoryIDRepository =
            instance ?: synchronized(this) {
                instance ?: StoryIDRepository(apiService, userPreference)
            }.also { instance = it }
    }
}