package com.app.pizzaandbeer.core.permission.internal.ui.fragment

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.app.pizzaandbeer.core.permission.internal.PermissionModule
import javax.inject.Inject

class PermissionActivity : AppCompatActivity() {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>


    lateinit var permissionModule: PermissionModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listOfPermissions = intent.extras?.getStringArray(
            KEY_PERMISSIONS
        ) ?: return

        val requestId = intent.extras?.getInt(
            KEY_REQUEST
        ) ?: return

        activityResultLauncher =
            this.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions(),
            ) {
                permissionModule.updateStateOfPermissions(
                    requestId,
                    it
                )
            }

        activityResultLauncher.launch(listOfPermissions)
    }

    companion object {
        internal const val KEY_PERMISSIONS = "KEY_PERMISSIONS"
        internal const val KEY_REQUEST = "KEY_REQUEST"
    }
}
