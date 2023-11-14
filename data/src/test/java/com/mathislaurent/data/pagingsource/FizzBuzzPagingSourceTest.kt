package com.mathislaurent.data.pagingsource

import androidx.paging.PagingSource
import com.mathislaurent.core.provider.FizzBuzzProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FizzBuzzPagingSourceTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK
    private lateinit var provider: FizzBuzzProvider

    @InjectMockKs
    private lateinit var pagingSource: FizzBuzzPagingSource

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun load_test() {
        runTest {
            // Given
            coEvery { provider(any(), any(), any(), any(), any()) } returns listOf(
                "hello",
                "world",
                "test",
                "1"
            )
            pagingSource.setArguments(3, 5, "fizz", "buzz")

            // When
            val result = pagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 2,
                    loadSize = 50,
                    placeholdersEnabled = false
                )
            )

            // Then
            assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
            assertThat((result as PagingSource.LoadResult.Page).data).hasSize(4)
            coVerify {
                provider(3, 5, "fizz", "buzz", IntRange(100, 150))
            }
        }
    }

}
