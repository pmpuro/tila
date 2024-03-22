package com.github.pmpuro.tila

import com.github.pmpuro.tila.api.DataId
import com.github.pmpuro.tila.api.DataMap
import com.github.pmpuro.tila.api.MutableDataMap
import com.github.pmpuro.tila.api.accessData
import com.github.pmpuro.tila.api.accessDataOrNull
import com.github.pmpuro.tila.api.deriveAppDataToStateDirectly
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.fail


class AccessTest {

    @Test
    fun `should access data of correct type`() = runTest {
        createData()
            .accessData<Int>(dataA)
            .let {
                assertEquals(it, value1)
            }

        createData()
            .accessDataOrNull<Int>(dataB)
            .let {
                assertEquals(it, value2)
            }
    }

    @Test
    fun `should throw for incorrect type accessData`() = runTest {
        assertFailsWith<IllegalArgumentException> {
            createData()
                .accessData<String>(dataA)
                .let {
                    fail("wrong type access should fail")
                }
        }
    }

    @Test
    fun `should throw for incorrect type accessDataOrNull`() = runTest {
        assertFailsWith<IllegalArgumentException> {
            createData()
                .accessDataOrNull<String>(dataB)
                .let {
                    fail("wrong type access should fail")
                }
        }
    }

    @Test
    fun `should throw for non existent data`() = runTest {
        assertFailsWith<IllegalStateException> {
            createData()
                .accessData<Int>(dataC)
                .let {
                    fail("data does not exists")
                }
        }
    }

    @Test
    fun `should return null for non existent data`() = runTest {
        createData()
            .accessDataOrNull<Int>(dataC)
            .let {
                assertNull(it, "data does not exists")
            }
    }

    @Test
    fun `should return the same data with destination key`() = runTest {
        val destination = DataId("d")
        createData()
            .deriveAppDataToStateDirectly<Int>(dataA, destination)
            .let {
                val result = it.accessData<Int>(destination)
                assertEquals(result, value1)
            }
    }

    private val dataA = DataId("a")
    private val dataB = DataId("b")
    private val dataC = DataId("c")
    private val value1 = 1
    private val value2 = 2
    private val map: MutableDataMap = mutableMapOf(dataA to value1, dataB to value2)
    private fun createData(): DataMap = map
}
