package com.dicoding.picodiploma.loginwithanimation.data.repository

import android.net.Uri
import com.dicoding.picodiploma.loginwithanimation.data.network.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun uploadStory(description: String, imageUri: Uri) {
        val user = runBlocking { userPreference.getSession().first() }
        val file = File(imageUri.path!!)
        val requestImageFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val imageMultipart = MultipartBody.Part.createFormData("photo", file.name, requestImageFile)
        val descriptionRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)

        apiService.uploadImage(imageMultipart, descriptionRequestBody)
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, userPreference)
            }.also { instance = it }
    }
}