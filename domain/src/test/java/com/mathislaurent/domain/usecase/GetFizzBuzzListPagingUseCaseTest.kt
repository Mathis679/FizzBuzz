package com.mathislaurent.domain.usecase

import androidx.paging.PagingData
import com.mathislaurent.data.repository.FizzBuzzRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFizzBuzzListPagingUseCaseTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    private lateinit var repository: FizzBuzzRepository

    @InjectMockKs
    private lateinit var useCase: GetFizzBuzzListPagingUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_test() {
        runTest {
            // Given
            coEvery { repository.getFizzBuzzPagingData(any(), any(), any(), any()) } returns flow {
                PagingData.from(listOf(
                    "test",
                    "test2"
                ))
            }

            // When
            val result = useCase(3, 5, "hello", "world")

            // Then
            assertThat(result).isNotNull
            coVerify { repository.getFizzBuzzPagingData(3, 5, "hello", "world") }
        }
    }
}
