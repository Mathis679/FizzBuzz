package com.mathislaurent.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mathislaurent.core.di.modules.IoDispatcher
import com.mathislaurent.data.pagingsource.FizzBuzzPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FizzBuzzRepository @Inject constructor(
    private val pagingSource: FizzBuzzPagingSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    companion object {
        const val PAGE_SIZE = 20
    }

    fun getFizzBuzzPagingData(
        firstInt: Int,
        secondInt: Int,
        firstWord: String,
        secondWord: String
    ) : Flow<PagingData<String>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                pagingSource.apply {
                    setArguments(
                        firstInt = firstInt,
                        secondInt = secondInt,
                        firstWord = firstWord,
                        secondWord = secondWord,
                    )
                }
            }
        ).flow.flowOn(dispatcher)
    }
}
