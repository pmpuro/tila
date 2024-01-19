package io.tila.impl

import androidx.compose.runtime.MutableState
import io.tila.api.DataId
import io.tila.api.DataMap
import io.tila.api.GenericValueState
import io.tila.api.MutableStateDataMap
import io.tila.api.StateData
import io.tila.api.StateDataList

class State(initialStateData: StateDataList = listOf()) {
    private val stateData: MutableStateDataMap = mutableMapOf()

    init {
        initialStateData.forEach { it.createInto(stateData) }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getData(id: DataId, defaultValue: T): MutableState<T> = stateData
        .getOrPut(
            id,
            defaultValue = { createGenericStateData(id, defaultValue) }
        )
        .toState() as MutableState<T>

    private fun <T> createGenericStateData(id: DataId, defaultValue: T): StateData =
        GenericValueState(id, defaultValue).apply { createInto(stateData) }

    @Suppress("UNCHECKED_CAST")
    fun <T> getExistingData(id: DataId): MutableState<T> = stateData
        .getOrElse(id) {
            error("Data $id is expected to be in State")
        }
        .toState() as MutableState<T>

    fun update(delta: DataMap) = delta.forEach { (key, newValue) ->
        if (stateData.containsKey(key)) {
            stateData[key]!!.setState(newValue)
        } else {
            val newData = createGenericStateData(key, newValue)
            stateData[key] = newData
        }
    }
}
