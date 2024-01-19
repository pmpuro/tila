package io.tila.impl

import io.tila.api.ApplyDerivative
import io.tila.api.ApplyEventHandler
import io.tila.api.DataMap
import io.tila.api.Derivative
import io.tila.api.EventHandler
import io.tila.api.EventId
import io.tila.api.Machine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope


class MachineImpl(
    data: DataMap = mapOf(),
    coroutineScope: CoroutineScope = MainScope(),
) : Machine {
    private val appData = Data(data.toMutableMap())
    private val derivativeApplier = ApplyDerivative { it(appData) }
    private val derivator = Derivator(derivativeApplier)
    private val eventHandlerApplier = ApplyEventHandler { handler, args -> handler(appData, args) }
    private val eventLoop = EventLoop(coroutineScope, eventHandlerApplier)
    override fun derive() = derivator.derive()
    override fun registerDerivative(derivative: Derivative): Boolean =
        derivator.registerDerivative(derivative)

    override fun deregisterDerivative(derivative: Derivative): Boolean =
        derivator.deregisterDerivative(derivative)

    override fun registerEventHandler(id: EventId, eventHandler: EventHandler) =
        eventLoop.registerEventHandler(id, eventHandler)

    override fun deregisterEventHandler(id: EventId) = eventLoop.deregisterEventHandler(id)
}

/*
    fun <T> injectState(id: DataId, defaultValue: T? = null): MutableState<T> =
        if (null == defaultValue) {
            state.getExistingData(id)
        } else {
            state.getData(id, defaultValue)
        }
 */
