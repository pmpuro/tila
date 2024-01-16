package io.tila.api

import io.tila.impl.Data


class Machine(data: DataMap = mapOf()) {
    private val appData = Data(data.toMutableMap())

}

/*
    fun <T> injectState(id: DataId, defaultValue: T? = null): MutableState<T> =
        if (null == defaultValue) {
            state.getExistingData(id)
        } else {
            state.getData(id, defaultValue)
        }
 */
