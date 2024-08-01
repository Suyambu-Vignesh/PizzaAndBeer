package com.app.pizzaandbeer.ui.view

import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.app.pizzaandbeer.R
import com.app.pizzaandbeer.core.location.LocationViewModel
import com.app.pizzaandbeer.core.location.internal.FetchingLocationState
import com.app.pizzaandbeer.core.location.internal.NoLocationPermissionState
import com.app.pizzaandbeer.data.error.NoLocationPermissionException
import com.app.pizzaandbeer.ui.model.ErrorState
import com.app.pizzaandbeer.ui.viewmodel.ProximityBusinessesViewModel

@Composable
fun ProximityBusiness(
    modifier: Modifier = Modifier.background(color = MaterialTheme.colors.primary),
    businessViewModel: ProximityBusinessesViewModel,
    locationViewModel: LocationViewModel,
    onItemClick: () -> Unit,
) {
    // By Default it will be false as not to ask any permission
    var askPermission by remember { mutableStateOf(false) }
    var locationState = locationViewModel.locationState.collectAsState()

    when {
        (locationState.value is NoLocationPermissionState) -> {
            ErrorView(state = ErrorState(NoLocationPermissionException())) {
                if (it == R.string.str_btn_location_permission) {
                    locationViewModel.askLocationPermission()
                }
            }
        }

        (locationState.value is FetchingLocationState) -> {
            PageProgressView()
        }

        else -> {
            ProximityBusinessListView(
                modifier,
                businessViewModel,
                onItemClick,
            ) {
                locationViewModel.askLocationPermission()
            }
        }
    }
}
