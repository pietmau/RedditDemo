package com.pietrantuono.common

interface UseCase<Params, Result> {

    suspend fun invoke(params: Params): Result
}