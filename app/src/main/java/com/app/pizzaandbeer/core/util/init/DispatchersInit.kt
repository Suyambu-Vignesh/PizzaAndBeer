package com.app.pizzaandbeer.core.util.init

import com.app.pizzaandbeer.core.util.CoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DispatchersInit {
    @Provides
    fun provideRemoteProximityBusinessRepository(): CoroutineDispatchers {
        return CoroutineDispatchers(
            ioDispatchers = Dispatchers.IO,
            defaultDispatchers = Dispatchers.Default,
            mainDispatchers = Dispatchers.Main,
        )
    }
}
