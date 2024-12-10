package com.dicoding.picodiploma.loginwithanimation.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.adapter.StoryAdapter
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.addstory.AddStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.detail.DetailActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var storyAdapter: StoryAdapter
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.listStory.observe(this) { stories ->
            stories?.let {
                storyAdapter = StoryAdapter(it) { story ->
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_STORY_ID, story.id)
                    startActivity(intent)
                }
                recyclerView.adapter = storyAdapter
            }
        }

        lifecycleScope.launch {
            val token = viewModel.getToken()
            viewModel.getStory(token)
        }

        findViewById<FloatingActionButton>(R.id.fab_add_story).setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }
}