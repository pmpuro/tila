package io.tila.api

import io.tila.impl.MachineImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

interface Machine : Derive, StateInjection, DerivativeManagement, EventHandlerManagement {
    fun createMachine(
        data: DataMap = mapOf(),
        initialStateData: StateDataList = listOf(),
        coroutineScope: CoroutineScope = MainScope(),
    ): Machine = MachineImpl(data, initialStateData, coroutineScope)
}
