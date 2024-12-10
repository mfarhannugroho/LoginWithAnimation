package com.dicoding.picodiploma.loginwithanimation.data.repository

import android.net.Uri
import com.dicoding.picodiploma.loginwithanimation.data.network.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.response.FileUploadResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.StoryResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class StoryRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    suspend fun getStories(): StoryResponse {
        return apiService.getStories()
    }

    suspend fun uploadStory(description: String, imageUri: Uri): FileUploadResponse {
        val file = File(imageUri.path!!)
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val descriptionBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
        return apiService.uploadImage(body, descriptionBody)
    }

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