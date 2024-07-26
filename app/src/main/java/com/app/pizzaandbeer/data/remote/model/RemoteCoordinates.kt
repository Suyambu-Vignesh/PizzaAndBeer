package com.app.pizzaandbeer.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteCoordinates(
    @Json(name = "latitude") var latitude: Double? = null,
    @Json(name = "longitude") var longitude: Double? = null,
)
