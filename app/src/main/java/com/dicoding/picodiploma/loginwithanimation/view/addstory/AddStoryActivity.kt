package com.dicoding.picodiploma.loginwithanimation.view.addstory

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity
import kotlinx.coroutines.launch

class AddStoryActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var descriptionEditText: EditText
    private val viewModel: AddStoryViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_story)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageView = findViewById(R.id.imagePreview)
        descriptionEditText = findViewById(R.id.descriptionInput)

        findViewById<Button>(R.id.btnSelectMedia).setOnClickListener {
            openGallery()
        }

        findViewById<Button>(R.id.btnCaptureMedia).setOnClickListener {
            openCamera()
        }

        findViewById<Button>(R.id.btnSubmitStory).setOnClickListener {
            uploadStory()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            imageView.setImageURI(selectedImageUri)
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = result.data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
            selectedImageUri = getImageUriFromBitmap(bitmap)
        }
    }

    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }

    private fun uploadStory() {
        val description = descriptionEditText.text.toString()
        val imageUri = selectedImageUri

        if (description.isNotEmpty() && imageUri != null) {
            lifecycleScope.launch {
                viewModel.uploadStory(description, imageUri)
                val intent = Intent(this@AddStoryActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }
}