package com.app.pizzaandbeer.core.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

object PermissionHandler {
    private val sharedFlow: MutableSharedFlow<PermissionResponse> by lazy { MutableSharedFlow(replay = 1) }
    internal val permissionResponseFlow: Flow<PermissionResponse> by lazy { sharedFlow }

    internal fun hasPermission(
        activity: Activity,
        permission: AppPermission,
    ): Boolean {
        return activity.checkSelfPermission(
            permission.permissionValue,
        ) == PackageManager.PERMISSION_GRANTED
    }

    internal fun askPermission(
        context: Context,
        permission: List<AppPermission>,
        requestId: Int,
    ) {
        val activity = context as? ComponentActivity ?: return
        activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) {
            // todo handle rationale
            val permissionResponse =
                PermissionResponse(
                    requestId,
                    it.getPermissionStatus(),
                )
            sharedFlow.tryEmit(
                permissionResponse,
            )
        }.launch(permission.getAsArray())
    }

    internal fun askPermission(
        activityResultLauncher: ActivityResultLauncher<Array<String>>,
        permissions: List<AppPermission>,
    ) {
        activityResultLauncher.launch(permissions.getAsArray())
    }
}

private fun Map<String, Boolean>.getPermissionStatus(): Map<AppPermission, Boolean> {
    val resultMap = HashMap<AppPermission, Boolean>()

    this.forEach { (permission, value) ->
        resultMap[AppPermission.valueOf(permission)] = value
    }

    return resultMap
}
