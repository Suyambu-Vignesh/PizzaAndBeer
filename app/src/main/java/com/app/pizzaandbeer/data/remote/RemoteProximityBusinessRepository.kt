package com.app.pizzaandbeer.data.remote

import com.app.pizzaandbeer.data.ProximityBusinessRepository
import com.app.pizzaandbeer.data.error.ClientRequestErrorException
import com.app.pizzaandbeer.data.error.EmptyBusinessInformationException
import com.app.pizzaandbeer.data.error.ServerErrorException
import com.app.pizzaandbeer.data.error.TailEndException
import com.app.pizzaandbeer.data.error.TooManyRequestException
import com.app.pizzaandbeer.data.model.ProximityServiceConfig
import com.app.pizzaandbeer.data.remote.datasource.ProximityBusinessDataSource
import com.app.pizzaandbeer.data.remote.model.RemoteProximityServices
import retrofit2.Response
import javax.inject.Inject

class RemoteProximityBusinessRepository
    @Inject
    constructor(
        private val proximityBusinessDataSource: ProximityBusinessDataSource,
    ) : ProximityBusinessRepository<ProximityServiceConfig, Result<RemoteProximityServices>> {
        override suspend fun getPizzaOrBeerBusiness(request: ProximityServiceConfig): Result<RemoteProximityServices> {
            return proximityBusinessDataSource.getProximityBusinessFor(
                request.term,
                request.getNonNullLatitude(),
                request.getNonNullLongitude(),
                request.offset.toString(),
            ).toResult()
        }
    }

private fun Response<RemoteProximityServices>.toResult(): Result<RemoteProximityServices> {
    val code = this.code()
    val body = this.body()

    return when {
        body != null && body.businesses?.isNotEmpty() == true -> {
            Result.success(body)
        }

        body != null && body.businesses.isNullOrEmpty() -> {
            Result.failure(EmptyBusinessInformationException())
        }

        code == 429 -> {
            Result.failure(TooManyRequestException())
        }

        code in 400..499 -> {
            Result.failure(ClientRequestErrorException(code = code))
        }

        code in 500..599 -> {
            Result.failure(ServerErrorException(code = code))
        }

        else -> {
            Result.failure(TailEndException())
        }
    }
}
