package com.github.pmpuro.tila.api

public interface StateData {
    public fun createInto(map: MutableStateDataMap)
    public fun setState(new: Any)
    public fun toState(): Any
}
