package io.tila.api

import io.tila.impl.Data


class Machine(data: DataMap = mapOf()) : Derive {
    private val appData = Data(data.toMutableMap())
    override fun derive() = derivatives.forEach { function -> appData.apply(function) }

    fun registerDerivative(derivative: Derivative) = derivatives.add(derivative)
    fun deregisterDerivative(derivative: (DataMap) -> Map<DataId, Any>) =
        derivatives.remove(derivative)

    private val derivatives: MutableList<Derivative> = mutableListOf()
}

/*
class Machine(
    private val data: Data = Data(),
    private val state: State = State(),
    coroutineScope: CoroutineScope = MainScope(),
) {
    init {
        coroutineScope.launch {
            processEvents()
        }
    }

    /** run all current events. */
    @Suppress("unused")
    fun run() = Unit

    @Suppress("unused")
    fun close() {
        queue.close()
    }

    fun <T> injectState(id: DataId, defaultValue: T? = null): MutableState<T> =
        if (null == defaultValue) {
            state.getExistingData(id)
        } else {
            state.getData(id, defaultValue)
        }

    fun createEvent(id: EventId, args: DataMap = mapOf()): () -> Unit = {
        queue
            .trySend(Event(id, args.toMap()))
            .getOrThrow()
    }

    fun registerEventHandlerFor(id: EventId, handler: EventHandler) =
        handler.also { handlers[id] = it }

    /* idea: scope to limit calls of derivative */
    fun registerDerivative(d: Derivative) = with(derivatives) { add(d) }

    private suspend fun processEvents() = queue
        .consumeAsFlow()
        .collect { incomingEvent ->
            with(incomingEvent) {
                val eventHandler = handlers[id] ?: error("Event handler for $id is missing")
                val map = apply(eventHandler, args)
                mergeData(map)
                derive()
            }
        }

    private fun mergeData(changed: DataMap) = changed.forEach { (k, v) ->
        data.set(k, v)
    }

    /* call a dedicated derivative when a scoped event processor is used/called */
    fun derive() = derivatives.forEach { function ->
        val deltaState = data.apply(function)
        state.update(deltaState)
    }

    private fun apply(eventHandler: EventHandler, args: DataMap) = data.apply(eventHandler, args)
    private val derivatives: MutableList<Derivative> = mutableListOf()
    private val handlers: MutableMap<EventId, EventHandler> = mutableMapOf()
    private data class Event(val id: EventId, val args: DataMap)
    private val queue = Channel<Event>(capacity = Channel.UNLIMITED)
}

 */
