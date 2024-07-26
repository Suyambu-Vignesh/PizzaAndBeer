package com.app.pizzaandbeer.core.location

import android.location.Location

/**
 * Interface to handle the location
 */
interface LocationHandler {
    /**
     * Method which handle the location
     *
     * @param location [Location]
     */
    fun handleLocation(location: Location)
}
