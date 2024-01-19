package io.tila.api

interface DerivativeManagement {
    fun registerDerivative(derivative: Derivative)
    fun deregisterDerivative(derivative: Derivative)
}
