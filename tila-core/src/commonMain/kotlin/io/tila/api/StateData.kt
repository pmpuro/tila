package io.tila.api

interface StateData {
    fun createInto(map: MutableStateDataMap)
    fun setState(new: Any)
    fun toState(): Any
}
