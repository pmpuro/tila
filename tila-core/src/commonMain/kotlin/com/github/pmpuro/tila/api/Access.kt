package com.github.pmpuro.tila.api

public inline fun <reified T> DataMap.accessData(id: DataId): T = get(id)
    ?.let {
        if (it is T) it
        else throw IllegalArgumentException("data $id is accessed as a wrong type")
    } ?: error("data $id does not exists")


public inline fun <reified T> DataMap.accessDataOrNull(id: DataId): T? = getOrElse(id) { null }
    ?.let {
        if (it is T) it
        else throw IllegalArgumentException("data $id is accessed as a wrong type")
    }
