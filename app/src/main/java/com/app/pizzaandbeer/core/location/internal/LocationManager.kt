package com.app.pizzaandbeer.core.location.internal

import android.annotation.SuppressLint
import android.content.Context
import com.app.pizzaandbeer.core.permission.AppPermission
import com.app.pizzaandbeer.core.permission.internal.PermissionModule
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        private val sharedLocationFlow: MutableSharedFlow<LocationState> by lazy { MutableSharedFlow<LocationState>() }
        internal val locationFlow: Flow<LocationState> by lazy { sharedLocationFlow }
        private val fusedLocationClient by lazy {
            LocationServices.getFusedLocationProviderClient(
                context,
            )
        }
        private val locationRequest by lazy {
            LocationRequest.create().apply {
                // todo all can be driven by config, [PRIORITY_HIGH_ACCURACY] we need to avoid on low battery
                interval = 30000
                fastestInterval = 30000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
        }

        private val locationCallback =
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let {
                        sharedLocationFlow.tryEmit(LocationInfoState(it))
                    }
                }
            }

        @SuppressLint("MissingPermission")
        internal fun startLocationEvent() {
            if (PermissionModule.hasPermission(
                    context, AppPermission.PERMISSION_ACCESS_COARSE_LOCATION,
                ) ||
                PermissionModule.hasPermission(
                    context,
                    AppPermission.PERMISSION_ACCESS_FINE_LOCATION,
                )
            ) {
                val lastLocation = fusedLocationClient.lastLocation

                if (lastLocation.isSuccessful && lastLocation.isComplete) {
                    sharedLocationFlow.tryEmit(LocationInfoState(lastLocation.result))
                }

                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            } else {
                sharedLocationFlow.tryEmit(NoLocationPermissionState)
            }
        }

        internal fun stopLocationEvent() {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }

        fun askLocationPermission(requestId: Int) {
            PermissionModule.askPermission(
                context,
                listOf(
                    AppPermission.PERMISSION_ACCESS_COARSE_LOCATION,
                    AppPermission.PERMISSION_ACCESS_FINE_LOCATION,
                ),
                requestId,
            )
        }
    }
