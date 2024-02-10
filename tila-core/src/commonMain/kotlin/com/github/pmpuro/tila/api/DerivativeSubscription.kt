package com.github.pmpuro.tila.api

public interface DerivativeSubscription {
    public fun registerDerivative(derivative: Derivative)
    public fun deregisterDerivative(derivative: Derivative)
}
