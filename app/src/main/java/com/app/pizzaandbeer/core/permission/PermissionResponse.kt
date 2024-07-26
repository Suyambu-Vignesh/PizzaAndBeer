package com.app.pizzaandbeer.core.permission

internal class PermissionResponse(
    val permissionRequestId: Int,
    val permissionStatus: Map<AppPermission, Boolean>,
)
