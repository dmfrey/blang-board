package io.pivotal.apptx.blangBoard.domain.common

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.time.Instant

class TimestampGeneratorTest {

    lateinit var timestampGenerator: TimestampGenerator

    @Before
    fun setup() {

        timestampGenerator = mock()

    }

    @Test
    fun testGenerate() {

        val timestamp = Instant.now()
        whenever( timestampGenerator.generate() ).thenReturn( timestamp )

        val expectedTimestamp = timestampGenerator.generate()
        assertThat( expectedTimestamp ).isEqualTo( timestamp )

        verify( timestampGenerator ).generate()

    }

}