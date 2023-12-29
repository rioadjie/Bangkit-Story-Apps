package com.rio.finalsubmissionandroidintermediate.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rio.finalsubmissionandroidintermediate.R
import com.rio.finalsubmissionandroidintermediate.databinding.ActivityLoginBinding
import com.rio.finalsubmissionandroidintermediate.live_data.pref.manager.ModelUser
import com.rio.finalsubmissionandroidintermediate.live_data.pref.repository.Results
import com.rio.finalsubmissionandroidintermediate.ui.FactoryView
import com.rio.finalsubmissionandroidintermediate.ui.data.model.LoginModel
import com.rio.finalsubmissionandroidintermediate.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginModel> {
        FactoryView.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupAction()
        animation()

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.iconBack.setOnClickListener { finishAffinity() }
    }


    private fun setupAction() {
        binding.bottonLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(email, password).observe(this) { result ->
                when (result) {
                    is Results.Loading -> {
                        showLoading(true)
                    }
                    is Results.Success -> {
                        val data = result.result.loginResult
                        data?.let {
                            viewModel.saveSession(ModelUser(it.name.toString(), it.userId.toString(), it.token.toString()))
                            navigateToHome()
                        }
                        showLoading(false)
                    }
                    is Results.Error -> {
                        showLoading(false)
                        Toast.makeText(this, R.string.empty_login, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun navigateToHome() {
        Intent(this, HomeActivity::class.java).apply {
            FactoryView.clearInstance()
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
            finish()
        }
    }

    private fun animation() {
        ObjectAnimator.ofFloat(binding.imageAdaptability, View.TRANSLATION_Y, -20f, 20f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titleAnim = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(1000)
        val messageAnim = ObjectAnimator.ofFloat(binding.texttitleLogin, View.ALPHA, 1f).setDuration(1000)
        val emailLayoutAnim = ObjectAnimator.ofFloat(binding.emailInput, View.ALPHA, 1f).setDuration(1500)
        val passwordLayoutAnim = ObjectAnimator.ofFloat(binding.textInputLayout, View.ALPHA, 1f).setDuration(1500)
        val loginButtonAnim = ObjectAnimator.ofFloat(binding.bottonLogin, View.ALPHA, 1f).setDuration(1000)
        val registerButtonAnim = ObjectAnimator.ofFloat(binding.registerLayout, View.ALPHA, 1f).setDuration(1000)

        AnimatorSet().apply {
            playSequentially(
                titleAnim,
                messageAnim,
                emailLayoutAnim,
                passwordLayoutAnim,
                loginButtonAnim,
                registerButtonAnim
            )
            startDelay = 100
        }.start()
    }



}
