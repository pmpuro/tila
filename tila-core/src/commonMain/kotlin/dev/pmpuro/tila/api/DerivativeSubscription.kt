package dev.pmpuro.tila.api

interface DerivativeSubscription {
    fun registerDerivative(derivative: Derivative)
    fun deregisterDerivative(derivative: Derivative)
}
