package com.app.pizzaandbeer.ui.domain

import com.app.pizzaandbeer.core.util.CoroutineDispatchers
import com.app.pizzaandbeer.data.ProximityBusinessRepository
import com.app.pizzaandbeer.data.error.TailEndException
import com.app.pizzaandbeer.data.impl.toBusinesses
import com.app.pizzaandbeer.data.model.ProximityServiceConfig
import com.app.pizzaandbeer.data.model.ProximityServices
import com.app.pizzaandbeer.fake.getRemoteBusinesses
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * Testsuite for [ProximityServiceBusinessUseCase]
 */
class ProximityServiceBusinessUseCaseTest {
    @Test
    fun `test ProximityServiceBusinessUseCase when both config return error`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val repository =
                mockk<ProximityBusinessRepository<ProximityServiceConfig, Result<ProximityServices>>>()

            val pizzaConfig = mockk<ProximityServiceConfig>()
            val beerConfig = mockk<ProximityServiceConfig>()

            val mockConfig = mockk<ProximityServiceConfig>()

            every { mockConfig.getNewConfig("pizza") } returns pizzaConfig
            every { mockConfig.getNewConfig("beer") } returns beerConfig

            coEvery { repository.getPizzaOrBeerBusiness(pizzaConfig) } returns
                Result.failure(
                    TailEndException(),
                )
            coEvery { repository.getPizzaOrBeerBusiness(beerConfig) } returns
                Result.failure(
                    TailEndException(),
                )

            val proximityServiceBusinessUseCase =
                ProximityServiceBusinessUseCase(
                    repository,
                    CoroutineDispatchers(dispatcher, dispatcher, dispatcher),
                )

            val result = proximityServiceBusinessUseCase.invoke(mockConfig)

            assertThat(result.isFailure).isTrue()
        }

    @Test
    fun `test ProximityServiceBusinessUseCase when any one of the config return error`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val repository =
                mockk<ProximityBusinessRepository<ProximityServiceConfig, Result<ProximityServices>>>()

            val pizzaConfig = mockk<ProximityServiceConfig>()
            val beerConfig = mockk<ProximityServiceConfig>()

            val mockConfig = mockk<ProximityServiceConfig>()

            every { mockConfig.getNewConfig("pizza") } returns pizzaConfig
            every { mockConfig.getNewConfig("beer") } returns beerConfig

            coEvery { repository.getPizzaOrBeerBusiness(pizzaConfig) } returns
                Result.success(
                    ProximityServices(
                        2,
                        listOf(
                            getRemoteBusinesses(
                                id = "id1",
                                name = "name1",
                            ).toBusinesses(),
                            getRemoteBusinesses(
                                id = "id2",
                                name = "name2",
                            ).toBusinesses(),
                        ).filterNotNull(),
                    ),
                )
            coEvery { repository.getPizzaOrBeerBusiness(beerConfig) } returns
                Result.failure(
                    TailEndException(),
                )

            val proximityServiceBusinessUseCase =
                ProximityServiceBusinessUseCase(
                    repository,
                    CoroutineDispatchers(dispatcher, dispatcher, dispatcher),
                )

            val result = proximityServiceBusinessUseCase.invoke(mockConfig)

            assertThat(result.isSuccess).isTrue()
            assertThat(result.getOrNull()?.size).isEqualTo(3)
        }
}
