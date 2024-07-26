package com.app.pizzaandbeer.ui.view

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.app.pizzaandbeer.core.permission.AppPermission
import com.app.pizzaandbeer.core.permission.PermissionHandler
import com.app.pizzaandbeer.ui.viewmodel.ProximityBusinessesViewModel

@Composable
fun ProximityBusiness(
    modifier: Modifier = Modifier.background(color = MaterialTheme.colors.primary),
    viewModel: ProximityBusinessesViewModel,
    activityResultLauncher: ActivityResultLauncher<Array<String>>,
    observeLocation: () -> Unit,
) {
    // By Default it will be false as not to ask any permission
    var askPermission by remember { mutableStateOf(false) }

    if (askPermission) {
        PermissionHandler.askPermission(
            activityResultLauncher,
            listOf(
                AppPermission.PERMISSION_ACCESS_COARSE_LOCATION,
                AppPermission.PERMISSION_ACCESS_FINE_LOCATION,
            ),
        )
        askPermission = false
    } else {
        ProximityBusinessListView(
            modifier,
            viewModel,
        ) {
            askPermission = true
        }
    }
}
