package com.github.pmpuro.tila

import com.github.pmpuro.tila.api.createMachine
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class CreateTest {

    @Test
    fun `should call the block`() = runTest {
        var blockCalled = 0

        val machine = createMachine(
            coroutineScope = this,
            block = {
                ++blockCalled
            }
        )
        machine.close()

        assertNotNull(machine)
        assertEquals(1, blockCalled)
    }

    @Test
    fun `should call derivatives`() = runTest {
        var derivativeCalled = 0

        createMachine(
            coroutineScope = this,
            block = {
                registerDerivative {
                    ++derivativeCalled
                    mapOf()
                }
            }
        )
            .close()

        assertEquals(1, derivativeCalled)
    }
}
