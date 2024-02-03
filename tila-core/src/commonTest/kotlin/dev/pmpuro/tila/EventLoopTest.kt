package dev.pmpuro.tila

import dev.pmpuro.tila.api.ApplyEventHandler
import dev.pmpuro.tila.api.DataMap
import dev.pmpuro.tila.api.EventHandler
import dev.pmpuro.tila.api.EventId
import dev.pmpuro.tila.impl.EventLoop
import io.mockative.Mock
import io.mockative.any
import io.mockative.classOf
import io.mockative.every
import io.mockative.mock
import io.mockative.verify
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
            registerEventHandler(eventId) { _, _ ->
                ++called
                mapOf()
            }

            createAndCallEvent(eventId)
            close()
        }

        advanceUntilIdle()
        assertEquals(1, called)
    }

    @Test
    fun `should be able to deregister an event handler`() = runTest {
        var called = 0
        createEventLoop().run {
            registerEventHandler(eventId) { _, _ ->
                ++called
                mapOf()
            }
            deregisterEventHandler(eventId)

            createAndCallEvent(eventId)
            close()
        }

        advanceUntilIdle()
        assertEquals(0, called)
    }

    @Test
    fun `should apply event handler`() = runTest {
        createEventLoop().run {
            registerEventHandler(eventId) { _, _ ->
                mapOf()
            }

            sendEvent(eventId)
            close()
        }

        advanceUntilIdle()
        verify { applier.apply(any(), any()) }.wasInvoked()
    }

    @Test
    fun `should send an event`() = runTest {
        createEventLoop().run {
            registerEventHandler(eventId) { _, _ ->
                mapOf()
            }

            sendEvent(eventId, mapOf())
            close()
        }

        advanceUntilIdle()
        verify { applier.apply(any(), any()) }.wasInvoked()
    }

    private val eventId = EventId("event")

    @Suppress("UNCHECKED_CAST")
    @Mock
    private val applier = mock(classOf<ApplyEventHandler>())
        .also {
            every { it.apply(any(), any()) }.invokes { arguments ->
                val handler = arguments[0] as EventHandler
                val args = arguments[1] as DataMap
                handler(mapOf(), args)
            }
        }

    private fun EventLoop.createAndCallEvent(id: EventId) {
        createEvent(id, mapOf())
            .also { it() }
    }

    private fun TestScope.createEventLoop(): EventLoop = EventLoop(this, applier)
}

