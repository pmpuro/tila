package io.tila

import io.mockative.Mock
import io.mockative.classOf
import io.mockative.every
import io.mockative.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.expect

class MyTest {

    @Mock val platform = mock(classOf<Platform>())

    @Test
    fun `should return platform name`() = runTest {
        val theName = "juu"
        every { platform.name }.returns(theName)

        AndroidPlatform()
            .getName(platform)
            .let {
                assertEquals(theName, it)
            }
    }
}
