package com.pietrantuono.common

interface Mapper<Input, Output> {

    fun map(input: Input): Output
}