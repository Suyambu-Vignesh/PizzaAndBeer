package com.app.pizzaandbeer.core.permission.internal.init

import com.app.pizzaandbeer.core.permission.PermissionApi
import com.app.pizzaandbeer.core.permission.internal.PermissionModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PermissionModuleInit {
    @Singleton
    @Provides
    fun provideHabitDatabase(): PermissionModule {
        return PermissionModule()
    }
}

