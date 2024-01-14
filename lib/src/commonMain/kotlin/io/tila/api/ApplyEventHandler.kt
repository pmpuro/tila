package io.tila.api

fun interface ApplyEventHandler {
    fun apply(eventHandler: EventHandler, args: DataMap): DataMap
}
