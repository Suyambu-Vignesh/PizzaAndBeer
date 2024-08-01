package com.app.pizzaandbeer.core.permission

import android.content.Context

/**
 * API which helps to check and request permission
 */
interface PermissionApi {
    /**
     * Method helps to check if the user has given consent to the permission
     *
     * @param context [Context]
     * @param permission [AppPermission]
     *
     * @return true if user has given consent
     */
    fun hasPermission(
        context: Context,
        permission: AppPermission,
    ): Boolean

    /**
     * Method to request the permission from customer
     *
     * @param context [Context]
     * @param permissions List of [AppPermission]
     * @param requestId context for the list of permission
     */
    fun askPermission(
        context: Context,
        permissions: List<AppPermission>,
        requestId: Int,
    )
}
