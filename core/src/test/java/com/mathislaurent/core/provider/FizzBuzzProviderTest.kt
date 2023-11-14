package com.mathislaurent.core.provider

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FizzBuzzProviderTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @InjectMockKs
    private lateinit var provider: FizzBuzzProvider

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_test() {
        runTest {
            // Given / When
            val result = provider(3, 5, "fizz", "buzz", IntRange(0, 20))

            // Then
            assertThat(result).hasSize(21)
            assertThat(result[3]).isEqualTo("fizz")
            assertThat(result[4]).isEqualTo("4")
            assertThat(result[5]).isEqualTo("buzz")
            assertThat(result[6]).isEqualTo("fizz")
            assertThat(result[7]).isEqualTo("7")
            assertThat(result[15]).isEqualTo("fizzbuzz")
        }
    }
}
