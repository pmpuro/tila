package dev.pmpuro.tila.api

public interface EventFactory {
    public fun createEvent(eventId: EventId, args: DataMap = mapOf()): () -> Unit
    public fun sendEvent(eventId: EventId, args: DataMap = mapOf())
}
