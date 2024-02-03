package dev.pmpuro.tila

import dev.pmpuro.tila.api.DataId
import dev.pmpuro.tila.api.DataMap
import dev.pmpuro.tila.api.MutableDataMap
import dev.pmpuro.tila.api.accessData
import dev.pmpuro.tila.api.accessDataOrNull
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
    fun `should throw for incorrect type`() = runTest {
        assertFailsWith<ClassCastException> {
            createData()
                .accessData<String>(dataA)
                .let {
                    fail("wrong type access should fail")
                }
        }
    }

    @Test
    fun `should return null for incorrect type`() = runTest {
        assertFailsWith<ClassCastException> {
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

    private val dataA = DataId("a")
    private val dataB = DataId("b")
    private val dataC = DataId("c")
    private val value1 = 1
    private val value2 = 2
    private val value3 = 3
    private val map: MutableDataMap = mutableMapOf(dataA to value1, dataB to value2)
    private fun createData(): DataMap = map
}