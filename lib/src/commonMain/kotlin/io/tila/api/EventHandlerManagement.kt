package io.tila.api

import io.tila.api.EventHandler
import io.tila.api.EventId

interface EventHandlerManagement {
    fun registerEventHandler(id: EventId, eventHandler: EventHandler)
    fun deregisterEventHandler(id: EventId)
}
