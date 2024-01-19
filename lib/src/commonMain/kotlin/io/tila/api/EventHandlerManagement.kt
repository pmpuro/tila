package io.tila.api

interface EventHandlerManagement {
    fun registerEventHandler(id: EventId, eventHandler: EventHandler)
    fun deregisterEventHandler(id: EventId)
}
