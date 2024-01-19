package io.tila.impl

import io.tila.api.ApplyDerivative
import io.tila.api.ApplyEventHandler
import io.tila.api.DataMap
import io.tila.api.Derivative
import io.tila.api.EventHandler
import io.tila.api.MutableDataMap

class Data(
    private val data: MutableDataMap
) : MutableDataMap by data, ApplyDerivative, ApplyEventHandler {
    override fun apply(function: Derivative): DataMap = function(data)
    override fun apply(
        eventHandler: EventHandler,
        args: DataMap
    ) = eventHandler(data, args)
}
