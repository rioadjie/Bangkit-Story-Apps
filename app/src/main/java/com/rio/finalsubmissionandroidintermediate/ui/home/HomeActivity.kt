package com.rio.finalsubmissionandroidintermediate.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rio.finalsubmissionandroidintermediate.databinding.ActivityHomeBinding
import com.rio.finalsubmissionandroidintermediate.ui.FactoryView
import com.rio.finalsubmissionandroidintermediate.ui.data.adapter.AdapterHome
import com.rio.finalsubmissionandroidintermediate.ui.data.adapter.AdapterLoading
import com.rio.finalsubmissionandroidintermediate.ui.data.model.HomeModel
import com.rio.finalsubmissionandroidintermediate.ui.welcome.LoginActivity
import com.rio.finalsubmissionandroidintermediate.ui.maps.MapsActivity
import com.rio.finalsubmissionandroidintermediate.ui.story.StoryActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    private val viewModel by viewModels<HomeModel> {
        FactoryView.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }


        playStories()

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        val ItemDecorations = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(ItemDecorations)

        binding.uploadStory.setOnClickListener {
            startActivity(Intent(this, StoryActivity::class.java))
        }

        binding.iconMaps.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        binding.iconSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }


    }

    private fun playStories() {
        val adapter = AdapterHome()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = AdapterLoading {
                adapter.retry()
            }
        )
        viewModel.getStory.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStory
    }


}
