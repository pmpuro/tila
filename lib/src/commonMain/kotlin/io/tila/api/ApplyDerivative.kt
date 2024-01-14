package io.tila.api

fun interface ApplyDerivative {
    fun apply(function: Derivative): DataMap
}
