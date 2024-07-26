package com.app.pizzaandbeer.data.remote.datasource.init

import com.app.pizzaandbeer.data.remote.datasource.ProximityBusinessDataSource
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProximityBusinessDataSourceInit {
    @Provides
    @Singleton
    fun providesDataSource(): ProximityBusinessDataSource {
        // todo url is added more statically either add in build config. Or Move to separate Module if provided the option to change env (debug)
        return Retrofit.Builder()
            .baseUrl("https://api.yelp.com")
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
            .create(ProximityBusinessDataSource::class.java)
    }
}
