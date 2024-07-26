package com.app.pizzaandbeer.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteProximityServices(
    @Json(name = "businesses") var businesses: List<RemoteBusinesses>? = null,
    @Json(name = "total") var total: Int? = null,
)
