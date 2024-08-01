package com.app.pizzaandbeer.core.permission

/**
 * state holder of Permission State
 *
 * @param permissionRequestId - context of the permission request
 * @param permissionStatus - State of Permission in map
 */
data class PermissionResponse(
    val permissionRequestId: Int,
    val permissionStatus: Map<AppPermission, Boolean>,
)
