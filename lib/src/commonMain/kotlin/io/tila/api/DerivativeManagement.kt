package io.tila.api

import io.tila.api.Derivative

interface DerivativeManagement {
    fun registerDerivative(derivative: Derivative): Boolean
    fun deregisterDerivative(derivative: Derivative): Boolean
}
