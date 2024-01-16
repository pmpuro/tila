package io.tila.impl

import io.tila.api.ApplyDerivative
import io.tila.api.Derivative
import io.tila.api.Derive


class Derivator(private val derivativeApplier: ApplyDerivative) : Derive {
    override fun derive() = derivatives.forEach { function -> derivativeApplier.apply(function) }

    fun registerDerivative(derivative: Derivative) = derivatives.add(derivative)
    fun deregisterDerivative(derivative: Derivative) = derivatives.remove(derivative)

    private val derivatives: MutableList<Derivative> = mutableListOf()
}
