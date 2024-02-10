package com.github.pmpuro.tila.api

public typealias MutableDataMap = MutableMap<DataId, Any>
public typealias DataMap = Map<DataId, Any>

public typealias MutableStateDataMap = MutableMap<DataId, StateData>
public typealias StateDataList = List<StateData>

/** app data, args --> app data */
public typealias EventHandler = (DataMap, DataMap) -> DataMap
/** app data --> ui data (as DataMap to be converted to StateMap) */
public typealias Derivative = (DataMap) -> DataMap
