package io.tila.impl

import io.tila.api.ApplyDerivative
import io.tila.api.Derivative
import io.tila.api.DerivativeSubscription
import io.tila.api.Derive


class Derivation(private val derivativeApplier: ApplyDerivative) : Derive, DerivativeSubscription {
    override fun derive() = derivatives.forEach { function -> derivativeApplier.apply(function) }

    override fun registerDerivative(derivative: Derivative) {
        derivatives.add(derivative)
    }

    override fun deregisterDerivative(derivative: Derivative) {
        derivatives.remove(derivative)
    }

    private val derivatives: MutableList<Derivative> = mutableListOf()
}
