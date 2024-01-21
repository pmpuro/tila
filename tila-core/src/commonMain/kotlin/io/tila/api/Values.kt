package io.tila.api

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


class GenericValueState<T>(
    private val id: DataId,
    private val initialValue: T,
    private val mutableState: MutableState<T> = mutableStateOf(initialValue),
) : StateData, MutableState<T> by mutableState {
    override fun createInto(map: MutableStateDataMap) {
        map[id] = this
    }

    @Suppress("UNCHECKED_CAST")
    override fun setState(new: Any) {
        val newValue = new as T
        mutableState.value = newValue
    }

    override fun toState(): Any = mutableState
}
