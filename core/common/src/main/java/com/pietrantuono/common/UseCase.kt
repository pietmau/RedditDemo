package com.pietrantuono.common

interface UseCase<Params, Result> {

    suspend fun execute(params: Params): Result
}