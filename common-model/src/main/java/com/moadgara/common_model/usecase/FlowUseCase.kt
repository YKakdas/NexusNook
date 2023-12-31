package com.moadgara.common_model.usecase

import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [NetworkResult].
 * Handling an exception (emit [NetworkResult.Failure] to the result) is the subclasses's responsibility.
 */
abstract class FlowUseCase<P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(parameters: P): Flow<NetworkResult<R>> = execute(parameters)
      .catch { e -> emit(NetworkResult.Failure(e.localizedMessage)) }
      .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<NetworkResult<R>>

}