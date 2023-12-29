package com.rio.finalsubmissionandroidintermediate.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rio.finalsubmissionandroidintermediate.databinding.ActivityIntroBinding
import com.rio.finalsubmissionandroidintermediate.ui.FactoryView
import com.rio.finalsubmissionandroidintermediate.ui.data.model.IntroModel
import com.rio.finalsubmissionandroidintermediate.ui.home.HomeActivity

class IntroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIntroBinding

    private val viewModel by viewModels<IntroModel> {
        FactoryView.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.bottonMulai.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }



    }
}
