package com.github.pmpuro.tila

import com.github.pmpuro.tila.api.DataId
import com.github.pmpuro.tila.api.EventId
import com.github.pmpuro.tila.impl.MachineImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalStdlibApi::class, ExperimentalCoroutinesApi::class)
class ApplyDerivativeTest {

    @Test
    fun `should call a derivative`() = runTest {
        createMachineImpl().use {
            with(it) {
                var called = 0
                registerDerivative { _ ->
                    ++called
                    mapOf()
                }
                derive()
                assertEquals(1, called)
                derive()
                assertEquals(2, called)
            }
        }
    }

    @Test
    fun `should derive state data from returned map from a derivative`() = runTest {
        createMachineImpl().use {
            with(it) {
                val dataList = listOf(id1 to data1, id2 to data2)
                registerDerivative { _ ->
                    dataList.toMap()
                }
                derive()
                dataList.forEach { (k, v) ->
                    val value = injectState<Int>(k).value
                    assertEquals(
                        v,
                        value,
                        "piece of state should be the same as returned from derivative"
                    )
                }
            }
        }
    }

    @Test
    fun `should derive automatically state data from returned map from a derivative`() = runTest {
        createMachineImpl().use {
            with(it) {
                val dataList = listOf(id1 to data1, id2 to data2)
                registerDerivative { _ ->
                    dataList.toMap()
                }
                registerEventHandler(eventId) { _, _ -> mapOf() }
                createEvent(eventId).apply { invoke() }
                advanceUntilIdle()
                dataList.forEach { (k, v) ->
                    val value = injectState<Int>(k).value
                    assertEquals(
                        v,
                        value,
                        "piece of state should be the same as returned from derivative"
                    )
                }
            }
        }
    }

    private val eventId = EventId("event id")
    private val id1 = DataId("id 1")
    private val id2 = DataId("id 2")
    private val data1 = 1
    private val data2 = 2
    private fun TestScope.createMachineImpl() = MachineImpl(coroutineScope = this)
}
