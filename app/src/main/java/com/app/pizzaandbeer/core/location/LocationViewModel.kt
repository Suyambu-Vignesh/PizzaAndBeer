package com.app.pizzaandbeer.core.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pizzaandbeer.core.location.internal.FetchingLocationState
import com.app.pizzaandbeer.core.location.internal.LocationManager
import com.app.pizzaandbeer.core.location.internal.LocationState
import com.app.pizzaandbeer.core.permission.internal.PermissionModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel
    @Inject
    constructor(
        private val locationManager: LocationManager,
        private val permissionManager: PermissionModule,
    ) : ViewModel() {
        companion object {
            private const val APP_PERMISSION_REQUEST_ID = 101
        }

        private val locationMutableState: MutableStateFlow<LocationState> by lazy {
            MutableStateFlow<LocationState>(
                FetchingLocationState,
            )
        }

        /**
         * State of Location data can be No Permission, Fetching location. Location update.
         */
        internal val locationState: StateFlow<LocationState> by lazy { locationMutableState }

        init {
            viewModelScope.launch {
                locationManager.locationFlow.collect {
                    locationMutableState.value = it
                }
            }

            viewModelScope.launch {
                permissionManager.permissionResponseFlow.collect {
                    if (it.permissionRequestId == APP_PERMISSION_REQUEST_ID &&
                        it.permissionStatus.values.contains(
                            true,
                        )
                    ) {
                        locationManager.startLocationEvent()
                    }
                }
            }

            viewModelScope.launch {
                locationManager.startLocationEvent()
            }
        }

        internal fun askLocationPermission() {
            locationManager.askLocationPermission(APP_PERMISSION_REQUEST_ID)
        }

        override fun onCleared() {
            super.onCleared()
            locationManager.stopLocationEvent()
        }
    }
