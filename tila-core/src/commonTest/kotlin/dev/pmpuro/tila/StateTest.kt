package dev.pmpuro.tila

import dev.pmpuro.tila.api.DataId
import dev.pmpuro.tila.api.GenericValueState
import dev.pmpuro.tila.impl.State
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StateTest {
    @Test
    fun `should return existing piece of state`() {
        State(listOf(GenericValueState<Int>(id, storedValue))).run {
            assertEquals(storedValue, getExistingData<Int>(id).value)
        }
    }

    @Test
    fun `should store piece of state`() {
        State().run {
            val state = getData(id, storedValue)
            assertEquals(storedValue, state.value)
            assertEquals(storedValue, getExistingData<Int>(id).value)
        }
    }

    @Test
    fun `should throw for non existing piece of state`() {
        State().run {
            assertFailsWith<IllegalStateException> {
                getExistingData<Int>(id)
            }
        }
    }

    @Test
    fun `should store many values`() {
        val id1 = DataId("first")
        val id2 = DataId("seconds")
        val listData = listOf(id1 to storedFirstValue, id2 to storedSecondValue)

        State(listData.map { GenericValueState(it.first, it.second) }.toList())
            .also { state ->
                listData.forEach { (id, value) ->
                    assertEquals(value, state.getExistingData<Int>(id).value)
                }
            }
    }

    @Test
    fun `should not override old piece of state`() {
        State().run {
            getData(id, storedFirstValue)
            getData(id, storedSecondValue)
            val state = getExistingData<Int>(id)
            assertEquals(storedFirstValue, state.value)
        }
    }

    @Test
    fun `should update existing piece of state`() {
        State(listOf(GenericValueState(id, storedFirstValue))).run {
            update(mapOf(id to storedSecondValue))
            val state = getExistingData<Int>(id)
            assertEquals(storedSecondValue, state.value)
        }
    }

    @Test
    fun `should add a new piece of state when updating`() = runTest {
        State().run {
            update(mapOf(id to storedValue))
            val state = getExistingData<Int>(id)
            assertEquals(storedValue, state.value)
        }
    }

    private val id = DataId("id")
    private val storedValue = 1
    private val storedFirstValue = 1
    private val storedSecondValue = 2
}
