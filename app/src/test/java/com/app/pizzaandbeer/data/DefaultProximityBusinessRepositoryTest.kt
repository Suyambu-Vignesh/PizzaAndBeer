package com.app.pizzaandbeer.data

import com.app.pizzaandbeer.core.util.CoroutineDispatchers
import com.app.pizzaandbeer.data.error.AppNetworkException
import com.app.pizzaandbeer.data.error.ServerErrorException
import com.app.pizzaandbeer.data.error.TailEndException
import com.app.pizzaandbeer.data.impl.DefaultProximityBusinessRepository
import com.app.pizzaandbeer.data.model.ProximityServiceConfig
import com.app.pizzaandbeer.data.remote.model.RemoteProximityServices
import com.app.pizzaandbeer.fake.getRemoteBusinesses
import com.app.pizzaandbeer.fake.getRemoteProximityServices
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import java.lang.RuntimeException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Testsuite of [DefaultProximityBusinessRepository]
 */
class DefaultProximityBusinessRepositoryTest {
    @Test
    fun `test getPizzaOrBeerBusiness when remote data source return proper response`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val mockRemoteRepository =
                mockk<ProximityBusinessRepository<ProximityServiceConfig, Result<RemoteProximityServices>>>()
            val remoteResponse =
                Result.success(
                    getRemoteProximityServices(
                        businesses =
                            listOf(
                                getRemoteBusinesses(id = "id1", name = "Name 1"),
                                getRemoteBusinesses(id = "id2", name = "Name 2"),
                            ),
                        total = 10,
                    ),
                )

            coEvery { mockRemoteRepository.getPizzaOrBeerBusiness(any()) } returns remoteResponse

            val repository =
                DefaultProximityBusinessRepository(
                    mockRemoteRepository,
                    CoroutineDispatchers(
                        dispatcher,
                        dispatcher,
                        dispatcher,
                    ),
                )

            val response =
                repository.getPizzaOrBeerBusiness(
                    ProximityServiceConfig(
                        0,
                        0,
                    ),
                )

            assertThat(response.isSuccess).isTrue()
            assertThat(response.getOrNull()!!.businesses.size).isEqualTo(2)
            assertThat(response.getOrNull()!!.totalResult).isEqualTo(10)
        }

    @Test
    fun `test getPizzaOrBeerBusiness when remote data source return success but has improper response`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val mockRemoteRepository =
                mockk<ProximityBusinessRepository<ProximityServiceConfig, Result<RemoteProximityServices>>>()
            val remoteResponse =
                Result.success(
                    getRemoteProximityServices(
                        businesses =
                            listOf(
                                getRemoteBusinesses(id = "id1", name = "Name 1"),
                                getRemoteBusinesses(),
                                getRemoteBusinesses(id = "id2"),
                                getRemoteBusinesses(name = "name2"),
                            ),
                        total = 10,
                    ),
                )

            coEvery { mockRemoteRepository.getPizzaOrBeerBusiness(any()) } returns remoteResponse

            val repository =
                DefaultProximityBusinessRepository(
                    mockRemoteRepository,
                    CoroutineDispatchers(
                        dispatcher,
                        dispatcher,
                        dispatcher,
                    ),
                )

            val response =
                repository.getPizzaOrBeerBusiness(
                    ProximityServiceConfig(
                        0,
                        0,
                    ),
                )

            assertThat(response.isSuccess).isTrue()
            assertThat(response.getOrNull()!!.businesses.size).isEqualTo(1)
            assertThat(response.getOrNull()!!.totalResult).isEqualTo(10)
        }

    @Test
    fun `test getPizzaOrBeerBusiness handle unknown exception`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val mockRemoteRepository =
                mockk<ProximityBusinessRepository<ProximityServiceConfig, Result<RemoteProximityServices>>>()

            coEvery { mockRemoteRepository.getPizzaOrBeerBusiness(any()) } throws RuntimeException("Sample one")

            val repository =
                DefaultProximityBusinessRepository(
                    mockRemoteRepository,
                    CoroutineDispatchers(
                        dispatcher,
                        dispatcher,
                        dispatcher,
                    ),
                )

            val response =
                repository.getPizzaOrBeerBusiness(
                    ProximityServiceConfig(
                        0,
                        0,
                    ),
                )

            assertThat(response.isFailure).isTrue()
            assertThat(response.exceptionOrNull() is TailEndException).isTrue()
            assertThat((response.exceptionOrNull() as TailEndException).message).isEqualTo("Sample one")
        }

    @Test
    fun `test getPizzaOrBeerBusiness handle Network exception`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val mockRemoteRepository =
                mockk<ProximityBusinessRepository<ProximityServiceConfig, Result<RemoteProximityServices>>>()

            coEvery { mockRemoteRepository.getPizzaOrBeerBusiness(any()) } throws UnknownHostException("Sample one")

            val repository =
                DefaultProximityBusinessRepository(
                    mockRemoteRepository,
                    CoroutineDispatchers(
                        dispatcher,
                        dispatcher,
                        dispatcher,
                    ),
                )

            val response =
                repository.getPizzaOrBeerBusiness(
                    ProximityServiceConfig(
                        0,
                        0,
                    ),
                )

            assertThat(response.isFailure).isTrue()
            assertThat(response.exceptionOrNull() is AppNetworkException).isTrue()
            assertThat((response.exceptionOrNull() as AppNetworkException).isTimeOut).isFalse()
            assertThat((response.exceptionOrNull() as AppNetworkException).message).isEqualTo("Sample one")
        }

    @Test
    fun `test getPizzaOrBeerBusiness handle timeout exception`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val mockRemoteRepository =
                mockk<ProximityBusinessRepository<ProximityServiceConfig, Result<RemoteProximityServices>>>()

            coEvery { mockRemoteRepository.getPizzaOrBeerBusiness(any()) } throws
                SocketTimeoutException(
                    "Sample one",
                )

            val repository =
                DefaultProximityBusinessRepository(
                    mockRemoteRepository,
                    CoroutineDispatchers(
                        dispatcher,
                        dispatcher,
                        dispatcher,
                    ),
                )

            val response =
                repository.getPizzaOrBeerBusiness(
                    ProximityServiceConfig(
                        0,
                        0,
                    ),
                )

            assertThat(response.isFailure).isTrue()
            assertThat(response.exceptionOrNull() is AppNetworkException).isTrue()
            assertThat((response.exceptionOrNull() as AppNetworkException).isTimeOut).isTrue()
            assertThat((response.exceptionOrNull() as AppNetworkException).message).isEqualTo("Sample one")
        }

    @Test
    fun `test getPizzaOrBeerBusiness passes exe from remote data source`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val mockRemoteRepository =
                mockk<ProximityBusinessRepository<ProximityServiceConfig, Result<RemoteProximityServices>>>()

            coEvery { mockRemoteRepository.getPizzaOrBeerBusiness(any()) } returns
                Result.failure(
                    ServerErrorException(503),
                )

            val repository =
                DefaultProximityBusinessRepository(
                    mockRemoteRepository,
                    CoroutineDispatchers(
                        dispatcher,
                        dispatcher,
                        dispatcher,
                    ),
                )

            val response =
                repository.getPizzaOrBeerBusiness(
                    ProximityServiceConfig(
                        0,
                        0,
                    ),
                )

            assertThat(response.isFailure).isTrue()
            assertThat(response.exceptionOrNull() is ServerErrorException).isTrue()
            assertThat((response.exceptionOrNull() as ServerErrorException).code).isEqualTo(503)
        }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
