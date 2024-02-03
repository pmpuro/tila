package dev.pmpuro.tila.api

import androidx.compose.runtime.MutableState

interface StateInjection {
    fun <T> injectState(id: DataId, defaultValue: T? = null): MutableState<T>
}
