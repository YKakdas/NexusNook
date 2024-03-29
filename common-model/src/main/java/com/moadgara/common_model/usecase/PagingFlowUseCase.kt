package com.moadgara.common_model.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class PagingFlowUseCase<in P, R : Any>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(parameters: P): Flow<PagingData<R>> = execute(parameters)
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<PagingData<R>>
}