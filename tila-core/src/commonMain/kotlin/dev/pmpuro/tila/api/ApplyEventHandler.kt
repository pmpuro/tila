package dev.pmpuro.tila.api

fun interface ApplyEventHandler {
    fun apply(eventHandler: EventHandler, args: DataMap): DataMap
}
