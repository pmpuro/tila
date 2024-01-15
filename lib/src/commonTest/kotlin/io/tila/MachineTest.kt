package io.tila

import io.tila.api.DataId
import io.tila.api.DataMap
import io.tila.api.Derivative
import io.tila.api.Machine
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class MachineTest {

    @Test
    fun `should call registered derivative`() = runTest {
        var called = 0
        createMachine().run {
            registerDerivative {
                ++called
                mapOf()
            }
            derive()
        }

        assertEquals(1, called)
    }

    @Test
    fun `should pass data map to derivatives`() = runTest {
        createMachine().run {
            registerDerivative { dataMap ->
                assertContains(dataMap, dataA)
                assertContains(dataMap, dataB)
                mapOf()
            }
            derive()
        }
    }

    @Test
    fun `should be able to deregister a derivative`() = runTest {
        createMachine().run {
            var called = 0
            val derivative: Derivative = {
                ++called
                mapOf()
            }

            registerDerivative(derivative)
            deregisterDerivative(derivative)
        }
    }
    private val dataA = DataId("a")
    private val dataB = DataId("b")
    private val value1 = 1
    private val value2 = 2
    private val map: DataMap = mapOf(dataA to value1, dataB to value2)
    private fun createMachine() = Machine(data = map)
}
