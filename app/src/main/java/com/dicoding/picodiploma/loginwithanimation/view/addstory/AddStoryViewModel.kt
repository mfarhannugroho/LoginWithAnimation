package com.dicoding.picodiploma.loginwithanimation.view.addstory

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    suspend fun uploadStory(description: String, imageUri: Uri) {
        withContext(Dispatchers.IO) {
            storyRepository.uploadStory(description, imageUri)
        }
    }
}