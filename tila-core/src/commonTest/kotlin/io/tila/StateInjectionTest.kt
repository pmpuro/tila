package io.tila

import androidx.compose.runtime.MutableState
import io.tila.api.DataId
import io.tila.api.Machine
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalStdlibApi::class)
class StateInjectionTest {

    @Test
    fun `should inject a new state`() = runTest {
        Machine(coroutineScope = this).use {
            with(it) {
                val state = injectState(id, valueA)
                assertEquals(valueA, state.value)
                val another = injectState(id, valueB)
                assertEquals(valueA, another.value)
            }
        }
    }

    @Test
    fun `should inject a state`() = runTest {
        Machine(coroutineScope = this).use {
            with(it) {
                val state = injectState(id, valueA)
                assertEquals(valueA, state.value)
                val another: MutableState<Int> = injectState(id)
                assertEquals(valueA, another.value)
            }
        }
    }

    private val id = DataId("id")
    private val valueA = 1
    private val valueB = 2
}
