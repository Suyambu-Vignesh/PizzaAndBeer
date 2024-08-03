package com.app.pizzaandbeer.ui.view

import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.pizzaandbeer.R
import com.app.pizzaandbeer.core.location.LocationViewModel
import com.app.pizzaandbeer.core.location.internal.FetchingLocationState
import com.app.pizzaandbeer.core.location.internal.LocationInfoState
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
    val locationState = locationViewModel.locationState.collectAsStateWithLifecycle()

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

        (locationState.value is LocationInfoState) -> {
            businessViewModel.setLatLongState(
                locationState.value as LocationInfoState
            )
            ProximityBusinessListView(
                modifier,
                businessViewModel,
                onItemClick,
            ) {
                locationViewModel.askLocationPermission()
            }
        }

        else -> {
        }
    }
}
