package com.rio.finalsubmissionandroidintermediate.ui.maps

import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.rio.finalsubmissionandroidintermediate.R
import com.rio.finalsubmissionandroidintermediate.databinding.ActivityMapsBinding
import com.rio.finalsubmissionandroidintermediate.live_data.pref.repository.Results
import com.rio.finalsubmissionandroidintermediate.live_data.response.ListStoryItem
import com.rio.finalsubmissionandroidintermediate.ui.FactoryView
import com.rio.finalsubmissionandroidintermediate.ui.data.model.MapsModel
import timber.log.Timber

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel by viewModels<MapsModel> {
        FactoryView.getInstance(this)
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getLocation().observe(this) { result ->
            when(result) {
                is Results.Loading -> { /* Handle loading state */ }
                is Results.Success -> {
                    applyMarker(result.result.listStory)
                }
                is Results.Error -> {
                    Toast.makeText(this, R.string.empty_data, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }

        binding.iconBack.setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setMapStyle()

        mMap.uiSettings.run {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        val dicodingSpace = LatLng(-6.8957643, 107.6338462)
        mMap.addMarker(
            MarkerOptions()
                .position(dicodingSpace)
                .title("Dicoding Space")
                .snippet("Batik Kumeli No.50")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dicodingSpace, 15f))

        applyMyLocation()

        viewModel.getLocation().observe(this) { result ->
            if (result is Results.Success) {
                applyMarker(result.result.listStory)
            }
        }
    }

    private fun applyMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            PermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val PermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                applyMyLocation()
            }
        }

    private val boundsBuilder = LatLngBounds.Builder()
    private fun applyMarker(stories: List<ListStoryItem>) {
        stories.forEach { story ->
            val latLng = LatLng(story.lat, story.lon)
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(story.name)
                    .snippet(story.description)
            )
            boundsBuilder.include(latLng)
        }

        val bound: LatLngBounds = boundsBuilder.build()

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bound,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                30
            )
        )
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Timber.tag(TAG).e("Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Timber.tag(TAG).e(exception, "Can't find style. Error: ")
        }
    }

    //use live template logt to create this
    companion object {
        private const val TAG = "MapsActivity"
    }
}
