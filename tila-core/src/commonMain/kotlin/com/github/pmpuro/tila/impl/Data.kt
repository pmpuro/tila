package com.github.pmpuro.tila.impl

import com.github.pmpuro.tila.api.ApplyDerivative
import com.github.pmpuro.tila.api.ApplyEventHandler
import com.github.pmpuro.tila.api.DataMap
import com.github.pmpuro.tila.api.Derivative
import com.github.pmpuro.tila.api.EventHandler
import com.github.pmpuro.tila.api.MutableDataMap

internal class Data(
    private val data: MutableDataMap
) : MutableDataMap by data, ApplyDerivative, ApplyEventHandler {
    override fun apply(function: Derivative): DataMap = function(data)
    override fun apply(
        eventHandler: EventHandler,
        args: DataMap
    ) = eventHandler(data, args)
}
