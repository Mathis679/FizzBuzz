package com.mathislaurent.core.provider

import com.mathislaurent.core.di.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FizzBuzzProvider @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        firstInt: Int,
        secondInt: Int,
        firstWord: String,
        secondWord: String,
        range: IntRange
    ) : List<String> = withContext(dispatcher) {
        range.map {
            when {
                it % (firstInt * secondInt) == 0 -> firstWord + secondWord
                it % firstInt == 0 -> firstWord
                it % secondInt == 0 -> secondWord
                else -> it.toString()
            }
        }
    }
}
