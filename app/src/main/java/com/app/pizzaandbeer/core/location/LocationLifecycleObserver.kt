package com.app.pizzaandbeer.core.location

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationLifecycleObserver(
    private val context: Context,
    private val locationHandler: LocationHandler,
) : LifecycleEventObserver {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest =
        LocationRequest.create().apply {
            // todo all can be driven by config, [PRIORITY_HIGH_ACCURACY] we need to avoid on low battery
            interval = 30000
            fastestInterval = 30000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

    private val locationCallback =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    locationHandler.handleLocation(location)
                }
            }
        }

    @SuppressLint("MissingPermission")
    override fun onStateChanged(
        source: LifecycleOwner,
        event: Lifecycle.Event,
    ) {
        when (event) {
            Lifecycle.Event.ON_START -> {
                // Start location updates
                val lastLocation = fusedLocationClient.lastLocation

                if (lastLocation.isSuccessful && lastLocation.isComplete) {
                    locationHandler.handleLocation(lastLocation.result)
                }

                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            }

            Lifecycle.Event.ON_STOP -> {
                // Stop location updates
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }

            else -> {
                // Do Nohing
            }
        }
    }
}
