package com.github.pmpuro.tila

import com.github.pmpuro.tila.api.DataId
import com.github.pmpuro.tila.api.MutableDataMap
import com.github.pmpuro.tila.impl.Data
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


class DataTest {

    @Test
    fun `should give the app data when a derivative is applied`() = runTest {
        createData()
            .apply { dataMap ->
                assertContains(dataMap, dataA)
                assertContains(dataMap, dataB)
                mapOf()
            }
    }

    @Test
    fun `should keep the app data the same after a derivate call`() = runTest {
        createData().run {
            apply { _ ->
                mapOf(dataC to value3)
            }

            apply { dataMap ->
                assertContains(dataMap, dataA)
                assertContains(dataMap, dataB)
                assertEquals(2, dataMap.size)
                mapOf(dataC to value3)
                mapOf()
            }
        }

    }

    private val dataA = DataId("a")
    private val dataB = DataId("b")
    private val dataC = DataId("c")
    private val value1 = 1
    private val value2 = 2
    private val value3 = 3
    private val map: MutableDataMap = mutableMapOf(dataA to value1, dataB to value2)
    private fun createData() = Data(data = map)
}
