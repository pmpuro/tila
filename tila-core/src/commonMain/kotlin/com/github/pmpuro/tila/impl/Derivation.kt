package com.github.pmpuro.tila.impl

import com.github.pmpuro.tila.api.ApplyDerivative
import com.github.pmpuro.tila.api.Derivative
import com.github.pmpuro.tila.api.DerivativeSubscription
import com.github.pmpuro.tila.api.Derive


internal class Derivation(private val derivativeApplier: ApplyDerivative) : Derive,
    DerivativeSubscription {
    override fun derive() = derivatives.forEach { function -> derivativeApplier.apply(function) }

    override fun registerDerivative(derivative: Derivative) {
        derivatives.add(derivative)
    }

    override fun deregisterDerivative(derivative: Derivative) {
        derivatives.remove(derivative)
    }

    private val derivatives: MutableList<Derivative> = mutableListOf()
}
