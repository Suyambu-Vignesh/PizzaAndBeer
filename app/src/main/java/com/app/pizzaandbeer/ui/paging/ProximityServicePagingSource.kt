package com.app.pizzaandbeer.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.pizzaandbeer.data.error.NoLocationPermissionException
import com.app.pizzaandbeer.data.error.TailEndException
import com.app.pizzaandbeer.data.model.ProximityServiceConfig
import com.app.pizzaandbeer.ui.domain.ProximityServiceBusinessUseCase
import com.app.pizzaandbeer.ui.model.ProximityServicePagingState

class ProximityServicePagingSource(
    private val latitude: Int? = null,
    private val longitude: Int? = null,
    private val proximityServiceBusinessUseCase: ProximityServiceBusinessUseCase,
) : PagingSource<ProximityServiceConfig, ProximityServicePagingState>() {
    companion object {
        private const val ERROR_PAGINATION_KEY_ISSUE = "Pagination Key is null"
    }

    override fun getRefreshKey(state: PagingState<ProximityServiceConfig, ProximityServicePagingState>): ProximityServiceConfig? {
        return ProximityServiceConfig(
            latitude,
            longitude,
        )
    }

    override suspend fun load(params: LoadParams<ProximityServiceConfig>): LoadResult<ProximityServiceConfig, ProximityServicePagingState> {
        var currentConfig =
            params.key ?: return LoadResult.Error(
                TailEndException(
                    ERROR_PAGINATION_KEY_ISSUE,
                ),
            )

        if (latitude == null || longitude == null) {
            return LoadResult.Error(
                NoLocationPermissionException(),
            )
        }

        currentConfig =
            currentConfig.getNewConfig(
                latitude,
                longitude,
            )

        return proximityServiceBusinessUseCase(currentConfig).fold(
            {
                LoadResult.Page(
                    data = it,
                    prevKey = currentConfig.prevConfig(),
                    nextKey = currentConfig.nextConfig(),
                )
            },
            {
                LoadResult.Error(it)
            },
        )
    }
}
