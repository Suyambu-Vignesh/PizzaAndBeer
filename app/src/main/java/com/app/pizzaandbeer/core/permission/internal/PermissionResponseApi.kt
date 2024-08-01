package com.app.pizzaandbeer.core.permission.internal

// todo make permission its own platform/core module

/**
 * This is more an internal private API used within Permission module.
 */
internal interface PermissionResponseApi {
    /**
     * Method to update the state of PermissionResponse
     *
     * @param requestId - context of
     * @param response - Map of PermissionResponse
     */
    fun updateStateOfPermissions(
        requestId: Int,
        response: Map<String, Boolean>,
    )
}
