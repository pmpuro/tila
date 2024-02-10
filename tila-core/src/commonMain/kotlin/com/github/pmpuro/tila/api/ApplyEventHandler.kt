package com.github.pmpuro.tila.api

public fun interface ApplyEventHandler {
    public fun apply(eventHandler: EventHandler, args: DataMap): DataMap
}
