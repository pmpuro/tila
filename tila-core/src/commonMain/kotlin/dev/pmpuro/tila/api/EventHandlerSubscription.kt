package dev.pmpuro.tila.api

public interface EventHandlerSubscription {
    public fun registerEventHandler(id: EventId, eventHandler: EventHandler)
    public fun deregisterEventHandler(id: EventId)
}
