package io.tila.api

class Data(initialData: DataMap = mapOf()) : ApplyDerivative, ApplyEventHandler {

    private val data: MutableDataMap = initialData.toMutableMap()

    fun set(key: DataId, value: Any) {
        data[key] = value
    }

    override fun apply(function: Derivative): DataMap = function(data)
    override fun apply(eventHandler: EventHandler, args: DataMap): DataMap =
        eventHandler(data, args)
}
