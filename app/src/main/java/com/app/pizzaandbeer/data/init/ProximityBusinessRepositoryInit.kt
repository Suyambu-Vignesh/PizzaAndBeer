package com.app.pizzaandbeer.data.init

import com.app.pizzaandbeer.core.util.CoroutineDispatchers
import com.app.pizzaandbeer.data.ProximityBusinessRepository
import com.app.pizzaandbeer.data.impl.DefaultProximityBusinessRepository
import com.app.pizzaandbeer.data.model.ProximityServiceConfig
import com.app.pizzaandbeer.data.model.ProximityServices
import com.app.pizzaandbeer.data.remote.RemoteProximityBusinessRepository
import com.app.pizzaandbeer.data.remote.datasource.ProximityBusinessDataSource
import com.app.pizzaandbeer.data.remote.model.RemoteProximityServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ProximityBusinessRepositoryInit {
    @Provides
    fun provideRemoteProximityBusinessRepository(
        proximityBusinessDataSource: ProximityBusinessDataSource,
    ): ProximityBusinessRepository<ProximityServiceConfig, Result<RemoteProximityServices>> {
        return RemoteProximityBusinessRepository(proximityBusinessDataSource)
    }

    @Provides
    fun providesProximityBusinessRepository(
        remoteProximityBusinessRepository: ProximityBusinessRepository<ProximityServiceConfig, Result<RemoteProximityServices>>,
        coroutineDispatchers: CoroutineDispatchers,
    ): ProximityBusinessRepository<ProximityServiceConfig, Result<ProximityServices>> {
        return DefaultProximityBusinessRepository(
            remoteProximityBusinessRepository,
            coroutineDispatchers,
        )
    }
}
