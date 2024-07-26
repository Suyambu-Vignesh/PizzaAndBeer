package com.app.pizzaandbeer.data.model

import com.app.pizzaandbeer.core.AppConfig

data class ProximityServiceConfig(
    val latitude: Int?,
    val longitude: Int?,
    val term: String = "",
    val offset: Int = 0,
) {
    fun getNewConfig(term: String): ProximityServiceConfig {
        return ProximityServiceConfig(
            latitude,
            longitude,
            term,
            offset,
        )
    }

    fun getNewConfig(
        latitude: Int,
        longitude: Int,
    ): ProximityServiceConfig {
        return if (this.longitude == latitude && this.longitude == longitude) {
            return this
        } else {
            ProximityServiceConfig(
                latitude,
                longitude,
                term,
                offset,
            )
        }
    }

    /**
     * Return a non null latitude. If null return the default latitude
     */
    fun getNonNullLatitude(): Int {
        return latitude ?: AppConfig.DEFAULT_LATITUDE
    }

    /**
     * Return a non null longitude. If null return the default longitude
     */
    fun getNonNullLongitude(): Int {
        return longitude ?: AppConfig.DEFAULT_LONGITUDE
    }

    fun nextConfig(): ProximityServiceConfig? {
        return ProximityServiceConfig(
            latitude,
            longitude,
            term,
            offset + AppConfig.NUMBER_OF_ITEM_PER_PAGE,
        )
    }

    fun prevConfig(): ProximityServiceConfig? {
        if (offset - AppConfig.NUMBER_OF_ITEM_PER_PAGE < 0) {
            return null
        }

        return ProximityServiceConfig(
            latitude,
            longitude,
            term,
            offset - AppConfig.NUMBER_OF_ITEM_PER_PAGE,
        )
    }
}
