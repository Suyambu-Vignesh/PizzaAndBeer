package com.app.pizzaandbeer.core.permission.internal

import android.content.Context
import android.content.pm.PackageManager
import com.app.pizzaandbeer.core.permission.AppPermission
import com.app.pizzaandbeer.core.permission.PermissionApi
import com.app.pizzaandbeer.core.permission.PermissionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

// todo handle this in it as its own module. with lifecyle, sp we can defer the initialization of its
// till needed.
object PermissionModule : PermissionApi, PermissionResponseApi {
    private val sharedFlow: MutableSharedFlow<PermissionResponse> by lazy { MutableSharedFlow(replay = 1) }
    internal val permissionResponseFlow: Flow<PermissionResponse> by lazy { sharedFlow }

    override fun hasPermission(
        context: Context,
        permission: AppPermission,
    ): Boolean {
        return context.checkSelfPermission(
            permission.permissionValue,
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun askPermission(
        context: Context,
        permissions: List<AppPermission>,
        requestId: Int,
    ) {
        val permissionMap = HashMap<AppPermission, Boolean>()
        val permissionNeedToBeAsked = ArrayList<AppPermission>()

        for (permission in permissions) {
            if (hasPermission(context, permission)) {
                permissionMap[permission] = true
            } else {
                permissionNeedToBeAsked.add(permission)
            }
        }

        sharedFlow.tryEmit(
            PermissionResponse(
                requestId,
                permissionMap,
            ),
        )
    }

    override fun updateStateOfPermissions(
        requestId: Int,
        response: Map<String, Boolean>,
    ) {
        sharedFlow.tryEmit(
            PermissionResponse(
                requestId,
                response.getPermissionStatus(),
            ),
        )
    }
}

private fun Map<String, Boolean>.getPermissionStatus(): Map<AppPermission, Boolean> {
    val resultMap = HashMap<AppPermission, Boolean>()

    this.forEach { (permission, value) ->
        resultMap[AppPermission.valueOf(permission)] = value
    }

    return resultMap
}
