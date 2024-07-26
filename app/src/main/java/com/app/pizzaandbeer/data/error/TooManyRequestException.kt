package com.app.pizzaandbeer.data.error

import java.lang.Exception

/**
 * Exception happens when there is a too many request made from particular account or service is getting
 * too many request which it cannot handle
 *
 * @param message [String]. Detail Info about the error
 */
class TooManyRequestException(
    override val message: String = "",
) : Exception()
