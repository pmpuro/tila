package dev.pmpuro.tila.impl

import dev.pmpuro.tila.api.ApplyEventHandler
import dev.pmpuro.tila.api.DataMap
import dev.pmpuro.tila.api.EventFactory
import dev.pmpuro.tila.api.EventHandler
import dev.pmpuro.tila.api.EventHandlerSubscription
import dev.pmpuro.tila.api.EventId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalStdlibApi::class)
internal class EventLoop(
    coroutineScope: CoroutineScope,
    private val applier: ApplyEventHandler,
) : EventHandlerSubscription, AutoCloseable, EventFactory {

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
                    applier.apply(handler, args)
                }
            }
        }

    override fun registerEventHandler(id: EventId, eventHandler: EventHandler) {
        handlers[id] = eventHandler
    }

    override fun deregisterEventHandler(id: EventId) {
        handlers.remove(id)
    }

    override fun createEvent(eventId: EventId, args: DataMap): () -> Unit = {
        queue
            .trySend(Event(eventId, args.toMap()))
            .getOrThrow()
    }

    override fun sendEvent(eventId: EventId, args: DataMap) {
        createEvent(eventId, args)()
    }

    override fun close() {
        queue.close()
    }
}
