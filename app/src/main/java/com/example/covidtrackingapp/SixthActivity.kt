package com.example.covidtrackingapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class SixthActivity : FragmentActivity(), OnMapReadyCallback {

    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var maps: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

       // fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@SixthActivity)
      //  fetchLocation()


      //  mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
      //  getLastLocation()

    }




    override fun onMapReady(googleMap: GoogleMap) {
        maps = googleMap
        enableMyLocation()

        googleMap.addMarker(
                MarkerOptions()
                        .position(LatLng(34.11550, -118.18433))
                        .title("Marker")
        )
        val latLng = LatLng(34.1550, -118.18433)
        var zoomLevel = 13.0f
        maps.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))

    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if(ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            maps.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
            )
        }
    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }


/*
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
                MarkerOptions()
                        .position(LatLng(lat, long))
                        .title("Marker")
        )

    }
 */

}