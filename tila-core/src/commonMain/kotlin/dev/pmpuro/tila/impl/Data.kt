package dev.pmpuro.tila.impl

import dev.pmpuro.tila.api.ApplyDerivative
import dev.pmpuro.tila.api.ApplyEventHandler
import dev.pmpuro.tila.api.DataMap
import dev.pmpuro.tila.api.Derivative
import dev.pmpuro.tila.api.EventHandler
import dev.pmpuro.tila.api.MutableDataMap

class Data(
    private val data: MutableDataMap
) : MutableDataMap by data, ApplyDerivative, ApplyEventHandler {
    override fun apply(function: Derivative): DataMap = function(data)
    override fun apply(
        eventHandler: EventHandler,
        args: DataMap
    ) = eventHandler(data, args)
}
