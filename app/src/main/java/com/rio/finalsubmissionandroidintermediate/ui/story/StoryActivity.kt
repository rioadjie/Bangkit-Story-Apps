package com.rio.finalsubmissionandroidintermediate.ui.story

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.rio.finalsubmissionandroidintermediate.R
import com.rio.finalsubmissionandroidintermediate.databinding.ActivityStoryBinding
import com.rio.finalsubmissionandroidintermediate.live_data.pref.repository.Results
import com.rio.finalsubmissionandroidintermediate.ui.FactoryView
import com.rio.finalsubmissionandroidintermediate.ui.data.model.StoryModel
import com.rio.finalsubmissionandroidintermediate.ui.home.HomeActivity
import com.rio.finalsubmissionandroidintermediate.ui.story.IntentCameraActivity.Companion.CAMERA_RESULT
import timber.log.Timber

class StoryActivity : AppCompatActivity() {

    private val viewModel by viewModels<StoryModel> {
        FactoryView.getInstance(this)
    }

    private lateinit var binding : ActivityStoryBinding

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, R.string.granted, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, R.string.denied, Toast.LENGTH_LONG).show()
            }
        }


    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.iconBack.setOnClickListener {
            finish()
        }

        binding.btnCamera.setOnClickListener { startCameraX() }
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnUpload.setOnClickListener { uploadImage() }


    }


    private fun uploadImage() {
        val description = binding.edtDescription.text.toString()
        if (description.isBlank()) {
            showToast(R.string.empty_image)
            return
        }

        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Timber.tag("Image File").d("showImage: %s", imageFile.path)

            viewModel.uploadImage(imageFile, description).observe(this) { result ->
                when (result) {
                    is Results.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Results.Success -> {
                        binding.progressBar.visibility = View.GONE
                        navigateToHomeActivity()
                    }
                    is Results.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, R.string.empty_upload, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        } ?: showToast(R.string.empty_images)
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }



    private val pickGallery = registerForActivityResult(
        ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Timber.tag("Photo Picker").d("No media selected")
        }
    }

    private fun startGallery() {
        val imageMimeType = "image/*"
        pickGallery.launch(imageMimeType)
    }


    private fun startCameraX() {
        val intent = Intent(this, IntentCameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_RESULT) {
            currentImageUri = it.data?.getStringExtra(IntentCameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Timber.tag("Image URI").d("showImage: %s", it)
            binding.imgPreview.setImageURI(it)
        }
    }



    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

    private fun showToast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
