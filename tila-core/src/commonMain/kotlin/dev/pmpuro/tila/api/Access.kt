package dev.pmpuro.tila.api

@Suppress("UNCHECKED_CAST")
fun <T> DataMap.accessData(id: DataId): T = get(id) as T

@Suppress("UNCHECKED_CAST")
fun <T> DataMap.accessDataOrNull(id: DataId): T? = getOrElse(id) { null } as T?
