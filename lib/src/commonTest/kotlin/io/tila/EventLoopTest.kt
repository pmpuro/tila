package io.tila

import io.tila.api.EventId
import io.tila.impl.EventLoop
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class EventLoopTest {

    @Test
    fun `should be able to register an event handler`() = runTest {
        var called = 0
        createEventLoop().run {
            registerEventHandler(eventId) { appData, args ->
                ++called
                mapOf()
            }

            createEvent(eventId, mapOf())
                .also { it() }
            close()
        }

        advanceUntilIdle()
        assertEquals(1, called)
    }

    @Test
    fun `should be able to deregister an event handler`() = runTest {
        var called = 0
        createEventLoop().run {
            registerEventHandler(eventId) { appData, args ->
                ++called
                mapOf()
            }
            deregisterEventHandler(eventId)

            createEvent(eventId, mapOf())
                .also { it() }
            close()
        }

        advanceUntilIdle()
        assertEquals(0, called)
    }

    private val eventId = EventId("event")
    private fun TestScope.createEventLoop(): EventLoop = EventLoop(this)
}

