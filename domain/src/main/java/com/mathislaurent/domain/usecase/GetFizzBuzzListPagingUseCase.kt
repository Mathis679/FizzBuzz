package com.mathislaurent.domain.usecase

import androidx.paging.PagingData
import com.mathislaurent.core.di.modules.DefaultDispatcher
import com.mathislaurent.data.repository.FizzBuzzRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFizzBuzzListPagingUseCase @Inject constructor(
    private val repository: FizzBuzzRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(
        firstInt: Int,
        secondInt: Int,
        firstWord: String,
        secondWord: String
    ): Flow<PagingData<String>> {
        return repository.getFizzBuzzPagingData(
            firstInt = firstInt,
            secondInt = secondInt,
            firstWord = firstWord,
            secondWord = secondWord
        ).distinctUntilChanged().flowOn(dispatcher)
    }
}
