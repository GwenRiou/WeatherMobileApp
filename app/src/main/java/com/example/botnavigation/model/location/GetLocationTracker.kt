package com.example.botnavigation.model.location

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

//TODO use dagerhilt to implente the location in MVVM style
class GetLocationTracker {
/*
    //check the permission
    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestcode
            )
            false
        } else {
            true
        }
    }

    // FusedLocationProviderClient - Main class for receiving location updates.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // LocationRequest - Requirements for the location updates, i.e.,
    // how often you should receive updates, the priority, etc.
    private lateinit var locationRequest: LocationRequest

    // LocationCallback - Called when FusedLocationProviderClient
    // has a new Location
    private lateinit var locationCallback: LocationCallback

    // This will store current location info
    private var currentLocation: Location? = null


    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    locationRequest = LocationRequest().apply {
        // Sets the desired interval for
        // active location updates.
        // This interval is inexact.
        interval = TimeUnit.SECONDS.toMillis(60)

        // Sets the fastest rate for active location updates.
        // This interval is exact, and your application will never
        // receive updates more frequently than this value
        fastestInterval = TimeUnit.SECONDS.toMillis(30)

        // Sets the maximum time when batched location
        // updates are delivered. Updates may be
        // delivered sooner than this interval
        maxWaitTime = TimeUnit.MINUTES.toMillis(2)

        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult?.lastLocation?.let {
                currentLocation = locationByGps
                latitude = currentLocation.latitude
                longitude = currentLocation.longitude
                // use latitude and longitude as per your need
            } ?: {
                Log.d(TAG, "Location information isn't available.")
            }
        }
    }

    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

    val removeTask = fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    removeTask.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d(TAG, "Location Callback removed.")
        } else {
            Log.d(TAG, "Failed to remove Location Callback.")
        }
    }

 */
}