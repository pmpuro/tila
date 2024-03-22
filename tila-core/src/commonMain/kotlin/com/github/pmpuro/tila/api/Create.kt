package com.github.pmpuro.tila.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

public fun createMachine(
    data: DataMap = mapOf(),
    initialStateData: StateDataList = listOf(),
    coroutineScope: CoroutineScope = MainScope(),
    block: Machine.() -> Unit
): Machine = Machine(data, initialStateData, coroutineScope).apply(block).also { it.derive() }
