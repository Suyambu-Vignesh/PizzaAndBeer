package com.app.pizzaandbeer.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.pizzaandbeer.R
import com.app.pizzaandbeer.ui.model.ErrorState
import com.app.pizzaandbeer.ui.model.ProximityServiceBusinessState
import com.app.pizzaandbeer.ui.model.ProximityServicePagingState
import com.app.pizzaandbeer.ui.viewmodel.ProximityBusinessesViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProximityBusinessDetailView(
    modifier: Modifier = Modifier,
    viewModel: ProximityBusinessesViewModel,
    onItemClick: () -> Unit,
    askLocationPermission: () -> Unit,
) {
    val proximityBusinessesPagingItems: LazyPagingItems<ProximityServicePagingState> =
        viewModel.proximityServicePagingDataFlow.collectAsLazyPagingItems()

    val pageState =
        rememberPagerState {
            proximityBusinessesPagingItems.itemCount
        }

    HorizontalPager(state = pageState) { id ->

        proximityBusinessesPagingItems[id]?.let { pagingItem ->
            when {
                pagingItem is ProximityServiceBusinessState -> {
                    ProximityBusinessCardView(state = pagingItem, onItemClick = onItemClick)
                }
            }
        }
    }
}

@Composable
fun ProximityBusinessDetailView1(
    modifier: Modifier = Modifier,
    viewModel: ProximityBusinessesViewModel,
    onItemClick: () -> Unit,
    askLocationPermission: () -> Unit,
) {
    val proximityBusinessesPagingItems: LazyPagingItems<ProximityServicePagingState> =
        viewModel.proximityServicePagingDataFlow.collectAsLazyPagingItems()
    val listState: LazyListState = rememberLazyListState()

    LazyRow(
        state = listState,
        modifier = modifier,
    ) {
        items(proximityBusinessesPagingItems.itemCount) { index ->
            proximityBusinessesPagingItems[index]?.let { pagingItem ->
                when {
                    pagingItem is ProximityServiceBusinessState -> {
                        ProximityBusinessCardView(state = pagingItem, onItemClick = onItemClick)
                    }
                }
            }
        }

        proximityBusinessesPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        PageProgressView(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                        )
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        ErrorView(ErrorState((loadState.refresh as LoadState.Error).error)) {
                            if (it == R.string.str_btn_location_permission) {
                                askLocationPermission.invoke()
                            } else {
                                viewModel.refresh()
                            }
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        PageProgressView(
                            Modifier
                                .fillMaxWidth()
                                .fillParentMaxHeight(),
                        )
                    }
                }
            }
        }
    }
}
