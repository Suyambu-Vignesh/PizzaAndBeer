package com.app.pizzaandbeer.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteBusinesses(
    @Json(name = "id") val id: String? = null,
    @Json(name = "alias") val alias: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "image_url") val imageUrl: String? = null,
    @Json(name = "is_closed") val isClosed: Boolean? = null,
    @Json(name = "url") val url: String? = null,
    @Json(name = "review_count") val reviewCount: Int? = null,
    @Json(name = "categories") val categories: List<RemoteCategories>? = null,
    @Json(name = "rating") val rating: Double? = null,
    @Json(name = "coordinates") val coordinates: RemoteCoordinates? = null,
    @Json(name = "transactions") val transactions: List<String>? = null,
    @Json(name = "price") val price: String? = null,
    @Json(name = "location") val location: RemoteLocation? = null,
    @Json(name = "phone") val phone: String? = null,
    @Json(name = "display_phone") var displayPhone: String? = null,
    @Json(name = "distance") val distance: Double? = null,
)
