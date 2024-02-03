package dev.pmpuro.tila.impl

import androidx.compose.runtime.MutableState
import dev.pmpuro.tila.api.ApplyDerivative
import dev.pmpuro.tila.api.ApplyEventHandler
import dev.pmpuro.tila.api.DataId
import dev.pmpuro.tila.api.DataMap
import dev.pmpuro.tila.api.Derivative
import dev.pmpuro.tila.api.EventHandler
import dev.pmpuro.tila.api.EventId
import dev.pmpuro.tila.api.Machine
import dev.pmpuro.tila.api.StateDataList
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

    override fun sendEvent(eventId: EventId, args: DataMap) = eventLoop.sendEvent(eventId, args)

    fun mergeData(changed: DataMap) = changed.forEach { (k, v) ->
        appData[k] = v
    }

    override fun close() {
        eventLoop.close()
    }
}
