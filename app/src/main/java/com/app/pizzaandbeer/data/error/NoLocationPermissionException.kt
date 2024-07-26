package com.app.pizzaandbeer.data.error

/**
 * Exception thrown when there is no information on Location Permission
 */
class NoLocationPermissionException(
    override val message: String = "",
) : Exception()
