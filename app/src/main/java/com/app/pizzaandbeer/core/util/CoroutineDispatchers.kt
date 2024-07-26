package com.app.pizzaandbeer.core.util

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class CoroutineDispatchers
    @Inject
    constructor(
        val ioDispatchers: CoroutineDispatcher,
        val defaultDispatchers: CoroutineDispatcher,
        val mainDispatchers: CoroutineDispatcher,
    )
