package com.mathislaurent.ui.list

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import app.cash.turbine.test
import com.mathislaurent.domain.usecase.GetFizzBuzzListPagingUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [29])
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class FizzBuzzListViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK(relaxed = true)
    private lateinit var getFizzBuzzListPagingUseCase: GetFizzBuzzListPagingUseCase

    @SpyK
    private var savedStateHandle = SavedStateHandle()

    @InjectMockKs
    private lateinit var viewModel: FizzBuzzListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun init_test() {
        runTest {
            // Given
            viewModel.firstIntArg = 2
            viewModel.secondIntArg = 3
            viewModel.firstWordArg = "fizz"
            viewModel.secondWordArg = "buzz"
            coEvery { getFizzBuzzListPagingUseCase(2, 3, "fizz", "buzz") } returns flow {
                PagingData.from(listOf(
                    "test",
                    "test2"
                ))
            }

            // When
            viewModel.fizzBuzzListUiState.test {
                viewModel.loadData()

                // Then
                val result = awaitItem()
                assertThat(result).isNotNull

                coVerify { getFizzBuzzListPagingUseCase(2, 3, "fizz", "buzz") }
            }
        }
    }
}
