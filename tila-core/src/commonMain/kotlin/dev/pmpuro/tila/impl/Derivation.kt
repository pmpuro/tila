package dev.pmpuro.tila.impl

import dev.pmpuro.tila.api.ApplyDerivative
import dev.pmpuro.tila.api.Derivative
import dev.pmpuro.tila.api.DerivativeSubscription
import dev.pmpuro.tila.api.Derive


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
