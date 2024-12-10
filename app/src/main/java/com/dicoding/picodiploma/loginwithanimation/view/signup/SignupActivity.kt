package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import com.dicoding.picodiploma.loginwithanimation.data.factory.SignupViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.data.repository.UserRepository
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.loginwithanimation.data.network.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val signupViewModel: SignupViewModel by viewModels {
        SignupViewModelFactory(UserRepository.getInstance(UserPreference.getInstance(dataStore), ApiConfig.getApiService("")))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length < 8) {
                    binding.passwordEditTextLayout.error = "Password must be at least 8 characters"
                } else {
                    binding.passwordEditTextLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (password.length >= 8) {
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                    setPositiveButton("Lanjut") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            } else {
                binding.passwordEditTextLayout.error = "Password must be at least 8 characters"
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()
    }
}