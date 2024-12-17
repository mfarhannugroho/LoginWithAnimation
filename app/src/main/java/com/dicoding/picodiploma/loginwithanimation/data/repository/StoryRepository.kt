package com.dicoding.picodiploma.loginwithanimation.data.repository

import android.net.Uri
import android.util.Log
import com.dicoding.picodiploma.loginwithanimation.data.network.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun uploadStory(description: String, imageUri: Uri) {
        val user = userPreference.getSession().first()
        val file = File(imageUri.path!!)
        val requestImageFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val imageMultipart = MultipartBody.Part.createFormData("photo", file.name, requestImageFile)
        val descriptionRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
        val token = user.token

        try {
            val response = apiService.uploadImage(imageMultipart, descriptionRequestBody)
            Log.d("StoryRepository", "Upload successful: ${response.message}")
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("StoryRepository", "Upload failed: HTTP ${e.code()} ${e.message()}\nError body: $errorBody", e)
        } catch (e: Exception) {
            Log.e("StoryRepository", "Upload failed: ${e.message}", e)
        }
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