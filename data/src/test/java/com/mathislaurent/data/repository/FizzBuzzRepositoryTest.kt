package com.mathislaurent.data.repository

import app.cash.turbine.test
import com.mathislaurent.data.pagingsource.FizzBuzzPagingSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FizzBuzzRepositoryTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    private lateinit var pagingSource: FizzBuzzPagingSource

    @InjectMockKs
    private lateinit var repository: FizzBuzzRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun getFizzBuzzPagingData_test() {
        runTest {
            // Given
            coEvery { pagingSource.setArguments(any(), any(), any(), any()) } just runs
            coEvery { pagingSource.registerInvalidatedCallback(any()) } just runs

            // When
            repository.getFizzBuzzPagingData(2, 5, "fizz", "buzz").test {
                val result = awaitItem()

                // Then
                assertThat(result).isNotNull
                coVerify { pagingSource.setArguments(2, 5, "fizz", "buzz") }
            }
        }
    }
}
