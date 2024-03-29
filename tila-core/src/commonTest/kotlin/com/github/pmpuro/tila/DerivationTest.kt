package com.github.pmpuro.tila

import com.github.pmpuro.tila.api.ApplyDerivative
import com.github.pmpuro.tila.api.DataId
import com.github.pmpuro.tila.api.DataMap
import com.github.pmpuro.tila.api.Derivative
import com.github.pmpuro.tila.impl.Derivation
import io.mockative.Mock
import io.mockative.any
import io.mockative.classOf
import io.mockative.every
import io.mockative.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class DerivationTest {

    @Test
    fun `should call registered derivative`() = runTest {
        var called = 0

        createDerivation().run {
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
        createDerivation().run {
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
        var called = 0

        createDerivation().run {
            val derivative: Derivative = {
                ++called
                mapOf()
            }

            registerDerivative(derivative)
            deregisterDerivative(derivative)
            derive()
        }

        assertEquals(0, called)
    }

    private val dataA = DataId("a")
    private val dataB = DataId("b")
    private val value1 = 1
    private val value2 = 2
    private val map: DataMap = mapOf(dataA to value1, dataB to value2)

    @Suppress("UNCHECKED_CAST")
    @Mock
    private val applier = mock(classOf<ApplyDerivative>())
        .also {
            every { it.apply(any()) }.invokes { arguments ->
                val derivative = arguments[0] as Derivative
                derivative(map)
            }
        }

    private fun createDerivation() = Derivation(applier)
}
