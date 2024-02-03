package dev.pmpuro.tila.api

import dev.pmpuro.tila.impl.MachineImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

@OptIn(ExperimentalStdlibApi::class)
public interface Machine :
    Derive, StateInjection, EventFactory,
    DerivativeSubscription, EventHandlerSubscription,
    AutoCloseable {
    public companion object {
        public operator fun invoke(
            data: DataMap = mapOf(),
            initialStateData: StateDataList = listOf(),
            coroutineScope: CoroutineScope = MainScope(),
        ): Machine = MachineImpl(data, initialStateData, coroutineScope)
    }
}
