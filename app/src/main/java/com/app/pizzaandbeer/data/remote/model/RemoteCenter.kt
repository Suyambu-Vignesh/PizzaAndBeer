package com.app.pizzaandbeer.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteCenter(
    @Json(name = "longitude") var longitude: Double? = null,
    @Json(name = "latitude") var latitude: Double? = null,
)
