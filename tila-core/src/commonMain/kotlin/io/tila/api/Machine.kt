package io.tila.api

import io.tila.impl.MachineImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

@OptIn(ExperimentalStdlibApi::class)
interface Machine :
    Derive, StateInjection, EventFactory,
    DerivativeSubscription, EventHandlerSubscription,
    AutoCloseable {
    companion object {
        fun create(
            data: DataMap = mapOf(),
            initialStateData: StateDataList = listOf(),
            coroutineScope: CoroutineScope = MainScope(),
        ): Machine = MachineImpl(data, initialStateData, coroutineScope)
    }
}
