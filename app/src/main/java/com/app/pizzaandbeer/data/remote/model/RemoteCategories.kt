package com.app.pizzaandbeer.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteCategories(
    @Json(name = "alias") var alias: String? = null,
    @Json(name = "title") var title: String? = null,
)
