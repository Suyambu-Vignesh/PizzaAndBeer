package com.app.pizzaandbeer.core.permission

import android.Manifest

/**
 * List of Permission the App Supports
 */
enum class AppPermission(val permissionValue: String) {
    PERMISSION_ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),
    PERMISSION_ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
}

internal fun List<AppPermission>.getAsArray(): Array<String> {
    return Array<String>(this.size) { index ->
        this[index].permissionValue
    }
}
