package io.tila.impl

import io.tila.api.DataMap
import io.tila.api.EventHandler
import io.tila.api.EventId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class EventLoop(coroutineScope: CoroutineScope) {

    init {
        coroutineScope.launch { processEvents() }
    }

    private data class Event(val id: EventId, val args: DataMap)

    private val queue = Channel<Event>(capacity = Channel.UNLIMITED)
    private val handlers: MutableMap<EventId, EventHandler> = mutableMapOf()
    private suspend fun processEvents() = queue
        .consumeAsFlow()
        .collect { incomingEvent ->
            with(incomingEvent) {
                val handler = handlers[id]
                if (null != handler) {
                    handler(mapOf(), mapOf())
                }
            }
        }

    fun registerEventHandler(id: EventId, eventHandler: EventHandler) {
        handlers[id] = eventHandler
    }

    fun deregisterEventHandler(id: EventId) {
        handlers.remove(id)
    }

    fun createEvent(eventId: EventId, args: DataMap = mapOf()): () -> Unit = {
        queue
            .trySend(Event(eventId, args.toMap()))
            .getOrThrow()
    }

    fun close() {
        queue.close()
    }

}
