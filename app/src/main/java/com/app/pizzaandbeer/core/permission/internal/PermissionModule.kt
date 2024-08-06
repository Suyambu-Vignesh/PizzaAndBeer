package com.app.pizzaandbeer.core.permission.internal

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import com.app.pizzaandbeer.core.permission.AppPermission
import com.app.pizzaandbeer.core.permission.PermissionApi
import com.app.pizzaandbeer.core.permission.PermissionResponse
import com.app.pizzaandbeer.core.permission.internal.ui.fragment.PermissionActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

// todo handle this in it as its own module. with lifecyle, sp we can defer the initialization of its
// till needed.
class PermissionModule() : PermissionApi, PermissionResponseApi {
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
        val permissionNeedToBeAsked = ArrayList<String>()

        for (permission in permissions) {
            if (hasPermission(context, permission)) {
                permissionMap[permission] = true
            } else {
                permissionNeedToBeAsked.add(permission.permissionValue)
            }
        }

        sharedFlow.tryEmit(
            PermissionResponse(
                requestId,
                permissionMap,
            ),
        )

        val intent = Intent(context, PermissionActivity::class.java)
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(
            PermissionActivity.KEY_PERMISSIONS,
            permissionNeedToBeAsked.toTypedArray()
        )

        context.startActivity(intent)
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

fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}
