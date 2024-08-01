package com.app.pizzaandbeer.ui.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.pizzaandbeer.core.AppConfig
import com.app.pizzaandbeer.data.model.ProximityServiceConfig
import com.app.pizzaandbeer.ui.domain.ProximityServiceBusinessUseCase
import com.app.pizzaandbeer.ui.model.ProximityServicePagingState
import com.app.pizzaandbeer.ui.paging.ProximityServicePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProximityBusinessesViewModel
    @Inject
    constructor(
        private val proximityServiceBusinessUseCase: ProximityServiceBusinessUseCase,
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        companion object {
            private const val LIST_STATE_KEY = "listState"
        }

        internal val lazyListState: LazyListState =
            LazyListState(
                savedStateHandle.get<Int>(LIST_STATE_KEY + "_index") ?: 0,
                savedStateHandle.get<Int>(LIST_STATE_KEY + "_offset") ?: 0,
            )

        init {
            viewModelScope.launch {
                snapshotFlow { lazyListState.firstVisibleItemIndex to lazyListState.firstVisibleItemScrollOffset }.collectLatest {
                        (index, offset) ->
                    savedStateHandle[LIST_STATE_KEY + "_index"] = index
                    savedStateHandle[LIST_STATE_KEY + "_offset"] = offset
                }
            }
        }

        private val refreshTrigger = MutableStateFlow(System.currentTimeMillis())

        internal val proximityServicePagingDataFlow: Flow<PagingData<ProximityServicePagingState>> =
            refreshTrigger.flatMapLatest {
                Pager<ProximityServiceConfig, ProximityServicePagingState>(
                    config = PagingConfig(AppConfig.NUMBER_OF_ITEM_PER_PAGE),
                    initialKey = ProximityServiceConfig(null, null),
                ) {
                    ProximityServicePagingSource(
                        proximityServiceBusinessUseCase,
                    )
                }.flow
            }.cachedIn(viewModelScope)

        internal fun refresh() {
            refreshTrigger.value = System.currentTimeMillis()
        }
    }
