package com.app.pizzaandbeer.data.error

/**
 * Exception happens when there is a Network Exception like No Network, Timeout
 *
 * @param isTimeOut - true when timeout happens
 * @param message - Exception message for detail and logging
 */
class AppNetworkException(
    val isTimeOut: Boolean = false,
    override val message: String = "",
) : Exception()
