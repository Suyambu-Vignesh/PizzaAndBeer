package com.app.pizzaandbeer.data.error

import java.lang.Exception

/**
 * Exception happens when there is a empty result from service side(but 2xx).
 *
 * @param message [String]. Detail Info about the error
 */
class EmptyBusinessInformationException(
    override val message: String = "",
) : Exception()
