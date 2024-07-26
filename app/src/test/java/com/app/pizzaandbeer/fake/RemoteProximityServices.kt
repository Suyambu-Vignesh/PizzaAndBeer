package com.app.pizzaandbeer.fake

import com.app.pizzaandbeer.data.remote.model.RemoteBusinesses
import com.app.pizzaandbeer.data.remote.model.RemoteProximityServices

fun getRemoteProximityServices(
    businesses: List<RemoteBusinesses>? = null,
    total: Int? = null,
): RemoteProximityServices {
    return RemoteProximityServices(
        businesses = businesses,
        total = total,
    )
}

fun getRemoteBusinesses(
    id: String? = null,
    alias: String? = null,
    name: String? = null,
    imageUrl: String? = null,
    rating: Double? = null,
    reviewCount: Int? = null,
): RemoteBusinesses {
    return RemoteBusinesses(
        id,
        alias,
        name,
        imageUrl = imageUrl,
        rating = rating,
        reviewCount = reviewCount,
    )
}
