package com.dicoding.picodiploma.loginwithanimation.view.addstory

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddStoryViewModel(private val storyRepository: StoryRepository,
                        private val userRepository: UserRepository
) : ViewModel() {

    suspend fun uploadStory(description: String, imageUri: Uri) {
        withContext(Dispatchers.IO) {
            storyRepository.uploadStory(description, imageUri)
        }
    }

    suspend fun getToken(): String {
        return userRepository.getToken()
    }
}