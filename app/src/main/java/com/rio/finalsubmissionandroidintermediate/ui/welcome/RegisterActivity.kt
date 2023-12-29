package com.rio.finalsubmissionandroidintermediate.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.rio.finalsubmissionandroidintermediate.R
import com.rio.finalsubmissionandroidintermediate.databinding.ActivityRegisterBinding
import com.rio.finalsubmissionandroidintermediate.live_data.response.ResponseRegister
import com.rio.finalsubmissionandroidintermediate.ui.FactoryView
import com.rio.finalsubmissionandroidintermediate.ui.data.model.RegisterModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val signUpViewModel by viewModels<RegisterModel> {
        FactoryView.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()

        animations()

        binding.tvMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.iconBack.setOnClickListener { finish() }

    }


    private fun setupAction() {
        binding.bottonRegister.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val name = binding.nameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val isError = false
            if (!isError) {
                showLoading(true)
                lifecycleScope.launch {
                    try {
                        val response = signUpViewModel.register(name, email, password)
                        showLoading(false)
                        if (response.error == false) {
                            showSuccess(email)
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: HttpException) {
                        handleHttpException(e)
                    }
                }
            }
        }
    }

    private fun showSuccess(email: String) {
        val successMessage = getString(R.string.registration_success, email)
        AlertDialog.Builder(this@RegisterActivity).apply {
            setTitle(R.string.succes)
            setMessage(successMessage)
            setPositiveButton(R.string.nexts) { _, _ ->
                finish()
            }
            create()
            show()
        }
    }

    private fun handleHttpException(e: HttpException) {
        showLoading(false)
        val errorBody = e.response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorBody, ResponseRegister::class.java)
        Toast.makeText(this@RegisterActivity, errorResponse.message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun animations() {
        ObjectAnimator.ofFloat(binding.imageAdaptability, View.TRANSLATION_Y, -20f, 20f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titleAnim = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(1000)
        val nameTitleAnim = ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 1f).setDuration(1000)
        val messageAnim = ObjectAnimator.ofFloat(binding.texttitleRegister, View.ALPHA, 1f).setDuration(1000)
        val emailLayoutAnim = ObjectAnimator.ofFloat(binding.emailInput, View.ALPHA, 1f).setDuration(1500)
        val passwordLayoutAnim = ObjectAnimator.ofFloat(binding.textInputLayout, View.ALPHA, 1f).setDuration(1500)
        val registerButtonAnim = ObjectAnimator.ofFloat(binding.bottonRegister, View.ALPHA, 1f).setDuration(1000)
        val daftarButtonAnim = ObjectAnimator.ofFloat(binding.masukLayout, View.ALPHA, 1f).setDuration(1000)

        val together = AnimatorSet().apply {
            playTogether(registerButtonAnim, daftarButtonAnim)
        }

        AnimatorSet().apply {
            playSequentially(
                titleAnim,
                messageAnim,
                emailLayoutAnim,
                daftarButtonAnim,
                passwordLayoutAnim,
                nameTitleAnim,
                registerButtonAnim,
                together
            )
            startDelay = 100
        }.start()
    }

}