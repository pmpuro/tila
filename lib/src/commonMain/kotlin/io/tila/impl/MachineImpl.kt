package io.tila.impl

import androidx.compose.runtime.MutableState
import io.tila.api.ApplyDerivative
import io.tila.api.ApplyEventHandler
import io.tila.api.DataId
import io.tila.api.DataMap
import io.tila.api.Derivative
import io.tila.api.EventHandler
import io.tila.api.EventId
import io.tila.api.Machine
import io.tila.api.StateDataList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope


class MachineImpl(
    data: DataMap = mapOf(),
    initialStateData: StateDataList = listOf(),
    coroutineScope: CoroutineScope = MainScope(),
) : Machine {
    private val appData = Data(data.toMutableMap())
    private val uiState = State(initialStateData)
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

    override fun <T> injectState(id: DataId, defaultValue: T?): MutableState<T> =
        if (null == defaultValue) {
            uiState.getExistingData(id)
        } else {
            uiState.getData(id, defaultValue)
        }
}
