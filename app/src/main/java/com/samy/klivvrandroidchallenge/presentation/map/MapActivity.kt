package com.samy.klivvrandroidchallenge.presentation.map

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.samy.klivvrandroidchallenge.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.samy.klivvrandroidchallenge.util.Utils.myLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var name: String =""
    private var country: String =""
    private var latitude: Double? =null
    private var longitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        myLog("maps activity")
        try {
            name = intent.getStringExtra("name")?:""
            country = intent.getStringExtra("country")?:""
            latitude = intent.getDoubleExtra("lat", 33.733334)
            longitude = intent.getDoubleExtra("lon", 44.416668)

        }catch (e:Exception){
            myLog("e: $e")
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker at the specific coordinates and move the camera
        val location = LatLng(latitude!!, longitude!!)
        mMap.addMarker(MarkerOptions().position(location).title(name!!+", "+country!!))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

        // Show the info window immediately
        mMap.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
    }
}
