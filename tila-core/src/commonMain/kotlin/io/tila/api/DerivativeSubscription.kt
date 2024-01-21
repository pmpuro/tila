package io.tila.api

interface DerivativeSubscription {
    fun registerDerivative(derivative: Derivative)
    fun deregisterDerivative(derivative: Derivative)
}
