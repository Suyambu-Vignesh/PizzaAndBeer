package com.app.pizzaandbeer.core.permission.internal.ui.fragment

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionFragment : Fragment() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityResultLauncher =
            requireActivity().registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions(),
            ) {
            }
    }
}
