package com.app.pizzaandbeer.data.error

import java.lang.Exception

/**
 * Exception happens when there is client side error like (4xx) or config side error.
 *
 * @param code - errorCode
 * @param message [String]. Detail Info about the error
 */
class ClientRequestErrorException(
    code: Int,
    override val message: String = "",
) : Exception()
