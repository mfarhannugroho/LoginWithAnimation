    package com.dicoding.picodiploma.loginwithanimation.di

    import android.content.Context
    import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
    import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
    import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryIDRepository
    import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryRepository
    import com.dicoding.picodiploma.loginwithanimation.data.repository.UserRepository
    import kotlinx.coroutines.flow.first
    import kotlinx.coroutines.runBlocking

    object Injection {
        fun provideRepository(context: Context): UserRepository {
            val pref = UserPreference.getInstance(context.dataStore)

            val user = runBlocking { pref.getSession().first() }
            val apiService = ApiConfig.getApiService(user.token)
            return UserRepository.getInstance(pref, apiService)
        }

        fun provideRepositoryStory(context: Context): StoryRepository {
            val pref = UserPreference.getInstance(context.dataStore)
            val user = runBlocking { pref.getSession().first() }
            val apiService = ApiConfig.getApiService(user.token)
            return StoryRepository.getInstance(apiService, pref)
        }

        fun provideRepositoryStoryID(context: Context): StoryIDRepository {
            val pref = UserPreference.getInstance(context.dataStore)
            val user = runBlocking { pref.getSession().first() }
            val apiService = ApiConfig.getApiService(user.token)
            return StoryIDRepository.getInstance(apiService, pref)
        }
    }
