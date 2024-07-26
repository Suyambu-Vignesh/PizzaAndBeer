package com.app.pizzaandbeer.data.error

import java.lang.Exception

/**
 * Exception for the scenarios where we cannot drop this in one or other bucket
 *
 * @param message [String]. Detail Info about the error
 */
class TailEndException(
    override val message: String = "",
) : Exception()
