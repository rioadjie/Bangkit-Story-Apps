package com.rio.finalsubmissionandroidintermediate.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rio.finalsubmissionandroidintermediate.R
import com.rio.finalsubmissionandroidintermediate.databinding.ActivitySettingBinding
import com.rio.finalsubmissionandroidintermediate.ui.FactoryView
import com.rio.finalsubmissionandroidintermediate.ui.data.model.HomeModel
import com.rio.finalsubmissionandroidintermediate.ui.welcome.LoginActivity

class SettingActivity : AppCompatActivity() {


    private val viewModel by viewModels<HomeModel> {
        FactoryView.getInstance(this)
    }

    lateinit var  binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val language = binding.root.findViewById<Button>(R.id.botton_language)

        language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.iconBack.setOnClickListener { finish() }

        binding.tvLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }
}
