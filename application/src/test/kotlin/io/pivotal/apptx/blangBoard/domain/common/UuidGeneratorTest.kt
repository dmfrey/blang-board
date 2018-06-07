package io.pivotal.apptx.blangBoard.domain.common

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

class UuidGeneratorTest {

    lateinit var uuidGenerator: UuidGenerator

    @Before
    fun setup() {

        uuidGenerator = mock()

    }

    @Test
    fun testGenerate() {

        val uuid = UUID.randomUUID()
        whenever( uuidGenerator.generate() ).thenReturn( uuid )

        val expectedUuid = uuidGenerator.generate()
        assertThat( expectedUuid ).isEqualTo( uuid )

        verify( uuidGenerator ).generate()

    }

    @Test
    fun testGenerateString() {

        val uuid = UUID.randomUUID().toString()
        whenever( uuidGenerator.generateString() ).thenReturn( uuid )

        val expectedUuid = uuidGenerator.generateString()
        assertThat( expectedUuid ).isEqualTo( uuid )

        verify( uuidGenerator ).generateString()

    }

}