package com.app.pizzaandbeer.data.error

import java.lang.Exception

/**
 * Exception happens when there is a service side errors(5xx)
 *
 * @param code - Http errorCode
 * @param message [String]. Detail Info about the error
 */
class ServerErrorException(
    val code: Int,
    override val message: String = "",
) : Exception()
