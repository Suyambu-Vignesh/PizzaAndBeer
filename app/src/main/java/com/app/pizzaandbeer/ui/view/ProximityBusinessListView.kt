package com.app.pizzaandbeer.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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

@Composable
fun ProximityBusinessListView(
    modifier: Modifier = Modifier,
    viewModel: ProximityBusinessesViewModel,
    askLocationPermission: () -> Unit,
) {
    val proximityBusinessesPagingItems: LazyPagingItems<ProximityServicePagingState> =
        viewModel.proximityServicePagingDataFlow.collectAsLazyPagingItems()
    val listState: LazyListState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        items(proximityBusinessesPagingItems.itemCount) { index ->
            proximityBusinessesPagingItems[index]?.let { pagingItem ->
                when {
                    pagingItem is ProximityServiceBusinessState -> {
                        ProximityBusinessCardView(pagingItem)
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
