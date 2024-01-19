package io.tila.api

interface EventFactory {
    fun createEvent(eventId: EventId, args: DataMap = mapOf()): () -> Unit
}
