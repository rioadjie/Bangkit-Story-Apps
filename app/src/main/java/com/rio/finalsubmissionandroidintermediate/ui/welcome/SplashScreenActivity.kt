package com.rio.finalsubmissionandroidintermediate.ui.welcome

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rio.finalsubmissionandroidintermediate.R

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
