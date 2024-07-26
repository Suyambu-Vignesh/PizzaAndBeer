package com.app.pizzaandbeer.ui.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.pizzaandbeer.core.AppConfig
import com.app.pizzaandbeer.core.location.LocationHandler
import com.app.pizzaandbeer.data.model.ProximityServiceConfig
import com.app.pizzaandbeer.ui.domain.ProximityServiceBusinessUseCase
import com.app.pizzaandbeer.ui.model.ProximityServicePagingState
import com.app.pizzaandbeer.ui.paging.ProximityServicePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class ProximityBusinessesViewModel
    @Inject
    constructor(
        private val proximityServiceBusinessUseCase: ProximityServiceBusinessUseCase,
    ) : ViewModel(), LocationHandler {
        private val refreshTrigger = MutableStateFlow(System.currentTimeMillis())

        private val locationMutableState: MutableStateFlow<Pair<Int, Int>?> by lazy {
            MutableStateFlow<Pair<Int, Int>?>(null)
        }

        val locationStateFlow: StateFlow<Pair<Int, Int>?> by lazy { locationMutableState }

        val proximityServicePagingDataFlow: Flow<PagingData<ProximityServicePagingState>> =
            refreshTrigger
                .flatMapLatest {
                    Pager<ProximityServiceConfig, ProximityServicePagingState>(
                        config = PagingConfig(AppConfig.NUMBER_OF_ITEM_PER_PAGE),
                        initialKey = ProximityServiceConfig(null, null),
                    ) {
                        ProximityServicePagingSource(
                            locationStateFlow.value?.first,
                            locationStateFlow.value?.second,
                            proximityServiceBusinessUseCase,
                        )
                    }.flow
                }.cachedIn(viewModelScope)

        override fun handleLocation(location: Location) {
            // yelp needs more as int than double
            locationMutableState.value =
                Pair(
                    location.latitude.toInt(),
                    location.longitude.toInt(),
                )
        }

        fun refresh() {
            refreshTrigger.value = System.currentTimeMillis()
        }
    }
