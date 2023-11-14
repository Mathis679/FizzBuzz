package com.mathislaurent.ui.form

import app.cash.turbine.test
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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [29])
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class FizzBuzzFormViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @InjectMockKs
    private lateinit var viewModel: FizzBuzzFormViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun validateForm_test() {
        runTest {
            // Given / When
            viewModel.navigateToList.test {
                viewModel.validateForm(
                    firstInt = "3",
                    secondInt = "2",
                    firstWord = "fizz",
                    secondWord = "buzz"
                )

                // Then
                val init = awaitItem()
                assertThat(init).isNull()
                val result = awaitItem()
                assertThat(result).isNotNull
                assertThat(result?.firstInt).isEqualTo(3)
                assertThat(result?.secondInt).isEqualTo(2)
                assertThat(result?.firstWord).isEqualTo("fizz")
                assertThat(result?.secondWord).isEqualTo("buzz")
            }
        }
    }

    @Test
    fun validateForm_error_test() {
        runTest {
            // Given / When
            viewModel.formErrorState.test {
                viewModel.validateForm(
                    firstInt = "3.2",
                    secondInt = "",
                    firstWord = "",
                    secondWord = "buzz"
                )

                // Then
                awaitItem()
                val result = awaitItem()
                assertThat(result).isNotNull
                assertThat(result.firstInt).isEqualTo(FormErrorType.BAD_FORMAT)
                assertThat(result.secondInt).isEqualTo(FormErrorType.EMPTY)
                assertThat(result.firstWord).isEqualTo(FormErrorType.EMPTY)
                assertThat(result.secondWord).isNull()
            }
        }
    }

    @Test
    fun validateIntField_test() {
        runTest {
            // Given / When
            val badFormat = viewModel.validateIntField("1.2")
            val empty = viewModel.validateIntField("")
            val good = viewModel.validateIntField("2")

            // Then
            assertThat(badFormat).isEqualTo(FormErrorType.BAD_FORMAT)
            assertThat(empty).isEqualTo(FormErrorType.EMPTY)
            assertThat(good).isNull()
        }
    }

    @Test
    fun validateStringField_test() {
        runTest {
            // Given / When
            val empty = viewModel.validateStringField("")
            val good = viewModel.validateStringField("hello")

            // Then
            assertThat(empty).isEqualTo(FormErrorType.EMPTY)
            assertThat(good).isNull()
        }
    }
}
