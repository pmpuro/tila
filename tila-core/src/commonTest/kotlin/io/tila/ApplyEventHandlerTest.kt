package io.tila

import io.tila.api.DataId
import io.tila.api.DataMap
import io.tila.api.EventId
import io.tila.impl.MachineImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalStdlibApi::class, ExperimentalCoroutinesApi::class)
class ApplyEventHandlerTest {

    @Test
    fun `should call event handler`() = runTest {
        var called = 0
        createMachineImpl().use {
            with(it) {
                registerEventHandler(eventId) { _, _ ->
                    ++called
                    mapOf()
                }

                createEventAndCall(eventId)
            }
        }

        advanceUntilIdle()
        assertEquals(1, called)
    }

    @Test
    fun `should call event handler when send an event immediately`() = runTest {
        var called = 0
        createMachineImpl().use {
            with(it) {
                registerEventHandler(eventId) { _, _ ->
                    ++called
                    mapOf()
                }

                sendEvent(eventId)
            }
        }

        advanceUntilIdle()
        assertEquals(1, called)
    }

    @Test
    fun `should merge all given data`() = runTest {
        var called = 0
        val dataList = listOf(id1 to data1, id2 to data2)
        createMachineImpl().use {
            with(it) {
                mergeData(dataList.toMap())

                registerEventHandler(eventId) { appData, _ ->
                    dataList.forEach { (id, data) ->
                        val found = appData[id]
                        assertEquals(data, found, "merged data should be passed to event handlers")
                    }

                    ++called

                    mapOf()
                }

                createEventAndCall(eventId)
            }
        }

        advanceUntilIdle()
        assertEquals(1, called)
    }

    @Test
    fun `should merge event handlers returned data to app data`() = runTest {
        var called = 0
        createMachineImpl().use {
            with(it) {
                val eventHandlerReturnedData = mapOf(id1 to data1, id2 to data2)
                val returningEventHandler: (DataMap, DataMap) -> DataMap = { _, _ ->
                    eventHandlerReturnedData
                }
                val verifyingEventHandler: (DataMap, DataMap) -> DataMap = { appData, _ ->
                    eventHandlerReturnedData.forEach { (k, v) ->
                        val found = appData[k]
                        assertEquals(
                            v,
                            found,
                            "previously data returned from an event handler should be in app data"
                        )
                    }
                    ++called
                    mapOf()
                }

                registerEventHandler(eventId, returningEventHandler)
                createEventAndCall(eventId)
                advanceUntilIdle()

                deregisterEventHandler(eventId)
                registerEventHandler(anotherEventId, verifyingEventHandler)
                createEventAndCall(anotherEventId)
            }
        }

        advanceUntilIdle()
        assertEquals(1, called)
    }

    private fun MachineImpl.createEventAndCall(eventId: EventId) {
        createEvent(eventId).apply { invoke() }
    }

    private val eventId = EventId("event")
    private val anotherEventId = EventId("another event")
    private val id1 = DataId("id 1")
    private val id2 = DataId("id 2")
    private val data1 = 1
    private val data2 = 2
    private fun TestScope.createMachineImpl() = MachineImpl(coroutineScope = this)
}
