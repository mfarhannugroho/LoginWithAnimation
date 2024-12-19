package com.dicoding.picodiploma.loginwithanimation.view.detail

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryIDRepository
import com.dicoding.picodiploma.loginwithanimation.data.response.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailViewModel(private val storyIDRepository: StoryIDRepository) : ViewModel() {

    suspend fun getStoryDetail(storyId: String): Story? {
        return withContext(Dispatchers.IO) {
            val response = storyIDRepository.getStoryDetail(storyId).execute()
            response.body()?.story
        }
    }
}