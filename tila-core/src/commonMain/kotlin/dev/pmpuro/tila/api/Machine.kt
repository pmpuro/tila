package dev.pmpuro.tila.api

import dev.pmpuro.tila.impl.MachineImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

@OptIn(ExperimentalStdlibApi::class)
interface Machine :
    Derive, StateInjection, EventFactory,
    DerivativeSubscription, EventHandlerSubscription,
    AutoCloseable {
    companion object {
        operator fun invoke(
            data: DataMap = mapOf(),
            initialStateData: StateDataList = listOf(),
            coroutineScope: CoroutineScope = MainScope(),
        ): Machine = MachineImpl(data, initialStateData, coroutineScope)
    }
}
