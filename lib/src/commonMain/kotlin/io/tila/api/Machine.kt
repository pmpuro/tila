package io.tila.api

import io.tila.impl.Data
import io.tila.impl.Derivator
import io.tila.impl.EventLoop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope


class Machine(
    data: DataMap = mapOf(),
    coroutineScope: CoroutineScope = MainScope()
) : Derive {
    private val appData = Data(data.toMutableMap())
    private val derivativeApplier = ApplyDerivative { it(appData) }
    private val derivator = Derivator(derivativeApplier)
    private val eventHandlerApplier = ApplyEventHandler { handler, args -> handler(appData, args) }
    private val eventLoop = EventLoop(coroutineScope, eventHandlerApplier)
    override fun derive() = derivator.derive()
}

/*
    fun <T> injectState(id: DataId, defaultValue: T? = null): MutableState<T> =
        if (null == defaultValue) {
            state.getExistingData(id)
        } else {
            state.getData(id, defaultValue)
        }
 */
