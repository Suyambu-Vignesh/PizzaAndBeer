package com.app.pizzaandbeer.data.impl

import androidx.annotation.VisibleForTesting
import com.app.pizzaandbeer.core.util.CoroutineDispatchers
import com.app.pizzaandbeer.data.ProximityBusinessRepository
import com.app.pizzaandbeer.data.error.AppNetworkException
import com.app.pizzaandbeer.data.error.EmptyBusinessInformationException
import com.app.pizzaandbeer.data.error.TailEndException
import com.app.pizzaandbeer.data.model.Businesses
import com.app.pizzaandbeer.data.model.ProximityServiceConfig
import com.app.pizzaandbeer.data.model.ProximityServices
import com.app.pizzaandbeer.data.remote.model.RemoteBusinesses
import com.app.pizzaandbeer.data.remote.model.RemoteCategories
import com.app.pizzaandbeer.data.remote.model.RemoteProximityServices
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Primary Data Source for the View Layer.
 */
class DefaultProximityBusinessRepository
    @Inject
    constructor(
        private val remoteProximityBusinessRepository: ProximityBusinessRepository<ProximityServiceConfig, Result<RemoteProximityServices>>,
        private val dispatcher: CoroutineDispatchers,
    ) : ProximityBusinessRepository<ProximityServiceConfig, Result<ProximityServices>> {
        override suspend fun getPizzaOrBeerBusiness(request: ProximityServiceConfig): Result<ProximityServices> {
            return withContext(dispatcher.ioDispatchers) {
                try {
                    remoteProximityBusinessRepository.getPizzaOrBeerBusiness(request).fold(
                        {
                            it.toResultProximityServices()
                        },
                        {
                            Result.failure(it)
                        },
                    )
                } catch (exe: Exception) {
                    when (exe) {
                        is UnknownHostException -> {
                            Result.failure<ProximityServices>(
                                AppNetworkException(false, exe.message ?: ""),
                            )
                        }

                        is SocketTimeoutException -> {
                            Result.failure<ProximityServices>(
                                AppNetworkException(true, exe.message ?: ""),
                            )
                        }

                        else -> {
                            Result.failure<ProximityServices>(
                                TailEndException(exe.message ?: ""),
                            )
                        }
                    }
                }
            }
        }
    }

/**
 * Helps to convert Remote model to model which gets exposed by Data Layer. This conversion is needed
 * So View will not expose directly to Remote model which subject to change when there is a new api
 * also some formatting can be applied.
 *
 * @return [Businesses] or null
 */
@VisibleForTesting
internal fun RemoteProximityServices.toResultProximityServices(): Result<ProximityServices> {
    val total =
        total ?: return Result.failure<ProximityServices>(EmptyBusinessInformationException())
    val data =
        businesses ?: return Result.failure<ProximityServices>(EmptyBusinessInformationException())

    val listOfBusinesses =
        data.mapNotNull {
            it.toBusinesses()
        }

    if (listOfBusinesses.isEmpty()) {
        return Result.failure<ProximityServices>(EmptyBusinessInformationException())
    }

    return Result.success(
        ProximityServices(
            total,
            listOfBusinesses,
        ),
    )
}

/**
 * Helps to convert [RemoteBusinesses] to [Businesses] which gets exposed by Data Layer. When the id or name is null
 * it is consider as invalid data as we cannot use this in View.
 *
 * @return [Businesses] or null
 */
@VisibleForTesting
internal fun RemoteBusinesses.toBusinesses(): Businesses? {
    return if (this.id != null && this.name != null) {
        Businesses(
            id,
            name,
            imageUrl,
            reviewCount,
            categories?.toCategoriesList(),
            rating,
            transactions,
            price,
            location?.displayAddress,
            phone,
            displayPhone,
        )
    } else {
        null
    }
}

@VisibleForTesting
internal fun List<RemoteCategories>?.toCategoriesList(): List<String>? {
    if (this == null) {
        return null
    }

    return this.mapNotNull { it.title }
}
