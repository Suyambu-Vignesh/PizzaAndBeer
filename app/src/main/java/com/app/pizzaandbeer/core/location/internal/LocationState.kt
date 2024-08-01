package com.app.pizzaandbeer.core.location.internal

import android.location.Location

sealed class LocationState

// todo need to add Location turn off State

/**
 * State which says that the app don't have location permission
 */
data object NoLocationPermissionState : LocationState()

/**
 * State which says that the app is working to fetch location
 */
data object FetchingLocationState : LocationState()

/**
 * State which says has about the current [Location]
 */
data class LocationInfoState(val location: Location) : LocationState()
