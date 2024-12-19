package com.dicoding.picodiploma.loginwithanimation.view.detail

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.response.Story
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val storyId = intent.getStringExtra(EXTRA_STORY_ID)

        if (storyId != null) {
            lifecycleScope.launch {
                val story = viewModel.getStoryDetail(storyId)
                if (story != null) {
                    displayStoryDetail(story)
                }
            }
        }

        findViewById<Button>(R.id.back_button).setOnClickListener {
            finish()
        }
    }

    private fun displayStoryDetail(story: Story) {
        findViewById<TextView>(R.id.title_text).text = story.name
        findViewById<TextView>(R.id.description_text).text = story.description
        Glide.with(this)
            .load(story.photoUrl)
            .into(findViewById(R.id.header_image))
    }

    companion object {
        const val EXTRA_STORY_ID = "extra_story_id"
    }
}