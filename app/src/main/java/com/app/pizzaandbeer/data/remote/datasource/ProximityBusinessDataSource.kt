package com.app.pizzaandbeer.data.remote.datasource

import com.app.pizzaandbeer.core.AppConfig
import com.app.pizzaandbeer.data.remote.model.RemoteProximityServices
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ProximityBusinessDataSource {
    // todo adding authHeader this way is not recommended. We need to add OAuth Interceptor as this will be more same for every request
    @GET("v3/businesses/search")
    suspend fun getProximityBusinessFor(
        @Query("term")
        term: String,
        @Query("latitude")
        latitude: Int,
        @Query("longitude")
        longitude: Int,
        @Query("offset")
        offset: String,
        @Header("Authorization")
        authHeader: String = AppConfig.API_TOKEN,
    ): Response<RemoteProximityServices>
}
