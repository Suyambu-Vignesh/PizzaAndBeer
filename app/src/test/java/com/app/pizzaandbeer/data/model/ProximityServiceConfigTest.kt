package com.app.pizzaandbeer.data.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Testsuite for [ProximityServiceConfig]
 */
class ProximityServiceConfigTest {
    @Test
    fun `test getNewConfig for new keyword`() {
        val config =
            ProximityServiceConfig(
                37,
                -122,
                "term1",
            )

        val newConfig = config.getNewConfig("term2")

        assertThat(config.equals(newConfig)).isFalse()

        assertThat(config.term).isEqualTo("term1")
        assertThat(newConfig.term).isEqualTo("term2")
        assertThat(newConfig.latitude).isEqualTo(37)
        assertThat(newConfig.longitude).isEqualTo(-122)
    }

    @Test
    fun `test getNewConfig for new lat and long when both are same`() {
        val config =
            ProximityServiceConfig(
                37,
                -122,
                "term1",
            )

        val newConfig = config.getNewConfig(37, -122)

        assertThat(config.equals(newConfig)).isTrue()
    }

    @Test
    fun `test getNewConfig for new lat and long when both are different`() {
        val config =
            ProximityServiceConfig(
                37,
                -122,
                "term1",
            )

        val newConfig = config.getNewConfig(37, -88)

        assertThat(config.equals(newConfig)).isFalse()
        assertThat(newConfig.longitude).isEqualTo(-88)
        assertThat(config.longitude).isEqualTo(-122)
    }
}
