package com.app.pizzaandbeer.ui.domain

import com.app.pizzaandbeer.core.util.CoroutineDispatchers
import com.app.pizzaandbeer.data.ProximityBusinessRepository
import com.app.pizzaandbeer.data.error.EmptyBusinessInformationException
import com.app.pizzaandbeer.data.error.TailEndException
import com.app.pizzaandbeer.data.model.Businesses
import com.app.pizzaandbeer.data.model.ProximityServiceConfig
import com.app.pizzaandbeer.data.model.ProximityServices
import com.app.pizzaandbeer.ui.model.ProximityServiceBusinessState
import com.app.pizzaandbeer.ui.model.ProximityServicePagingState
import com.app.pizzaandbeer.ui.model.ProximityServiceResultCountHeaderState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProximityServiceBusinessUseCase
    @Inject
    constructor(
        private val repository: ProximityBusinessRepository<ProximityServiceConfig, Result<ProximityServices>>,
        private val coroutineDispatcher: CoroutineDispatchers,
    ) {
        suspend operator fun invoke(proximityServiceConfig: ProximityServiceConfig): Result<List<ProximityServicePagingState>> {
            return supervisorScope {
                withContext(coroutineDispatcher.defaultDispatchers) {
                    val pizzaJob =
                        async {
                            repository.getPizzaOrBeerBusiness(proximityServiceConfig.getNewConfig("pizza"))
                        }

                    val beerJob =
                        async {
                            repository.getPizzaOrBeerBusiness(proximityServiceConfig.getNewConfig("beer"))
                        }

                    val (responsePizza, responseBeer) = awaitAll(pizzaJob, beerJob)

                    val data =
                        when {
                            responsePizza.isFailure && responseBeer.isFailure -> {
                                // both failed
                                Result.failure(responsePizza.exceptionOrNull() ?: TailEndException())
                            }

                            responsePizza.isSuccess && responseBeer.isSuccess -> {

                                // both succeed
                                combine(responseBeer.getOrNull(), responsePizza.getOrNull())
                            }

                            responseBeer.isSuccess -> {
                                // only responseBeer succeed
                                responseBeer.toPagingItemsResult()
                            }

                            else -> {
                                // only responsePizza succeed
                                responsePizza.toPagingItemsResult()
                            }
                        }
                    data
                }
            }
        }
    }

private fun Result<ProximityServices>.toPagingItemsResult(): Result<List<ProximityServicePagingState>> {
    return this.fold(
        {
            if (it.businesses.isEmpty() || it.totalResult == 0) {
                Result.failure(EmptyBusinessInformationException())
            } else {
                val list = ArrayList<ProximityServicePagingState>()
                list.add(ProximityServiceResultCountHeaderState(it.totalResult.toString()))
                it.getItemsIn(list)
                Result.success(list)
            }
        },
        {
            Result.failure(it)
        },
    )
}

private fun combine(
    beerResponse: ProximityServices?,
    pizzaResponse: ProximityServices?,
): Result<List<ProximityServicePagingState>> {
    val (beerResponseIsEmpty, beerProximityServices) =
        beerResponse?.let {
            if (it.businesses.isEmpty() || it.totalResult == 0) {
                Pair(true, ProximityServices())
            } else {
                Pair(false, it)
            }
        } ?: run {
            Pair(true, ProximityServices())
        }

    val (pizzaResponseIsEmpty, pizzaProximityServices) =
        pizzaResponse?.let {
            if (it.businesses.isEmpty() || it.totalResult == 0) {
                Pair(true, ProximityServices())
            } else {
                Pair(false, it)
            }
        } ?: run {
            Pair(true, ProximityServices())
        }

    return if (beerResponseIsEmpty && pizzaResponseIsEmpty) {
        Result.failure(EmptyBusinessInformationException())
    } else {
        val totalResult = beerProximityServices.totalResult + pizzaProximityServices.totalResult
        val list = ArrayList<ProximityServicePagingState>()
        list.add(ProximityServiceResultCountHeaderState(totalResult.toString()))

        pizzaProximityServices.getItemsIn(list)
        beerProximityServices.getItemsIn(list)
        Result.success(list)
    }
}

private fun ProximityServices.getItemsIn(list: ArrayList<ProximityServicePagingState>) {
    for (business in this.businesses) {
        list.add(
            business.toProximityServiceBusinessItem(),
        )
    }
}

private fun Businesses.toProximityServiceBusinessItem(): ProximityServiceBusinessState {
    return ProximityServiceBusinessState(
        this.id,
        this.name,
        this.imageUrl,
        this.reviewCount?.toString(),
        this.categories,
        this.rating?.toString(),
        this.transactions,
        this.price,
        this.displayAddress,
        this.phone,
        this.displayPhone,
    )
}
