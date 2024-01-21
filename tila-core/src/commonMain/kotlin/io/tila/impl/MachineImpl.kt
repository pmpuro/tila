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
    private val derivativeApplier = ApplyDerivative { it(appData).also { uiState.update(it) } }
    private val derivation = Derivation(derivativeApplier)
    private val eventHandlerApplier =
        ApplyEventHandler { handler, args ->
            handler(appData, args).also { mergeData(it); derive() }
        }
    private val eventLoop = EventLoop(coroutineScope, eventHandlerApplier)
    override fun derive() = derivation.derive()
    override fun registerDerivative(derivative: Derivative) =
        derivation.registerDerivative(derivative)

    override fun deregisterDerivative(derivative: Derivative) =
        derivation.deregisterDerivative(derivative)

    override fun registerEventHandler(id: EventId, eventHandler: EventHandler) =
        eventLoop.registerEventHandler(id, eventHandler)

    override fun deregisterEventHandler(id: EventId) = eventLoop.deregisterEventHandler(id)
    override fun <T> injectState(id: DataId, defaultValue: T?): MutableState<T> =
        if (null == defaultValue) {
            uiState.getExistingData(id)
        } else {
            uiState.getData(id, defaultValue)
        }

    override fun createEvent(eventId: EventId, args: DataMap): () -> Unit =
        eventLoop.createEvent(eventId, args)

    fun mergeData(changed: DataMap) = changed.forEach { (k, v) ->
        appData[k] = v
    }

    override fun close() {
        eventLoop.close()
    }
}
