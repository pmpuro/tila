package io.tila.api

typealias MutableDataMap = MutableMap<DataId, Any>
typealias DataMap = Map<DataId, Any>

typealias MutableStateDataMap = MutableMap<DataId, StateData>
typealias StateDataList = List<StateData>

/** app data, args --> app data */
typealias EventHandler = (DataMap, DataMap) -> DataMap
/** app data --> ui data (as DataMap to be converted to StateMap) */
typealias Derivative = (DataMap) -> DataMap
