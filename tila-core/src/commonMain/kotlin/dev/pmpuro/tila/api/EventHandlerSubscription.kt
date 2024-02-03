package dev.pmpuro.tila.api

interface EventHandlerSubscription {
    fun registerEventHandler(id: EventId, eventHandler: EventHandler)
    fun deregisterEventHandler(id: EventId)
}
