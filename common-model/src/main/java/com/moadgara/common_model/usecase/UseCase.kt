package com.moadgara.common_model.usecase

import com.moadgara.common_model.network.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    /** Executes the use case asynchronously and returns a [NetworkResult].
     *
     * @return a [NetworkResult].
     *
     * @param param the input parameters to run the use case with
     */
    suspend operator fun invoke(param: P): NetworkResult<R> {
        return try {
            // Moving all use case's executions to the injected dispatcher
            // In production code, this is usually the Default dispatcher (background thread)
            // In tests, this becomes a TestCoroutineDispatcher
            withContext(coroutineDispatcher) {
                execute(param)
            }
        } catch (e: Exception) {
            NetworkResult.Failure(e.message)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(param: P): NetworkResult<R>
}