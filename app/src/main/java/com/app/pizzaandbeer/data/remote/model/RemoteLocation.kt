package com.app.pizzaandbeer.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteLocation(
    @Json(name = "address1") var address1: String? = null,
    @Json(name = "address2") var address2: String? = null,
    @Json(name = "address3") var address3: String? = null,
    @Json(name = "city") var city: String? = null,
    @Json(name = "zip_code") var zipCode: String? = null,
    @Json(name = "country") var country: String? = null,
    @Json(name = "state") var state: String? = null,
    @Json(name = "display_address") var displayAddress: List<String>? = null,
)
