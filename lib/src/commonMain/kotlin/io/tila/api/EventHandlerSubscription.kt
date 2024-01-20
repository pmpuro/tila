package io.tila.api

interface EventHandlerSubscription {
    fun registerEventHandler(id: EventId, eventHandler: EventHandler)
    fun deregisterEventHandler(id: EventId)
}
