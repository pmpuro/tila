package com.github.pmpuro.tila.api

import androidx.compose.runtime.MutableState

public interface StateInjection {
    public fun <T> injectState(id: DataId, defaultValue: T? = null): MutableState<T>
}
