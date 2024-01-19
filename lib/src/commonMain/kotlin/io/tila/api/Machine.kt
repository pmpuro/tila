package io.tila.api

import io.tila.impl.MachineImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

interface Machine : Derive, DerivativeManagement, EventHandlerManagement {
    fun createMachine(
        data: DataMap = mapOf(),
        coroutineScope: CoroutineScope = MainScope(),
    ): Machine = MachineImpl(data, coroutineScope)
}
