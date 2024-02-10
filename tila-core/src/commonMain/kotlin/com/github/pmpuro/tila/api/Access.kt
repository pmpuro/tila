package com.github.pmpuro.tila.api

@Suppress("UNCHECKED_CAST")
public fun <T> DataMap.accessData(id: DataId): T =
    get(id).let { it as T } ?: error("data $id does not exists")


@Suppress("UNCHECKED_CAST")
public fun <T> DataMap.accessDataOrNull(id: DataId): T? = getOrElse(id) { null } as T?
