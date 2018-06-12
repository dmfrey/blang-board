package io.pivotal.apptx.blangBoard.domain.usecases

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.pivotal.apptx.blangBoard.domain.Project
import io.pivotal.apptx.blangBoard.domain.Term
import io.pivotal.apptx.blangBoard.domain.common.TimestampGenerator
import io.pivotal.apptx.blangBoard.domain.common.UuidGenerator
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.service.CreateNewTermService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.util.*

class CreateNewTermTests {

    lateinit var subject: CreateNewTerm

    lateinit var mockProjectGroupRepository: ProjectRepository
    lateinit var mockUuidGenerator: UuidGenerator
    lateinit var mockTimestampGenerator: TimestampGenerator

    @Before
    fun setup() {

        mockProjectGroupRepository = mock()
        mockUuidGenerator = mock()
        mockTimestampGenerator = mock()

        subject = CreateNewTermService( mockProjectGroupRepository, mockUuidGenerator, mockTimestampGenerator )

    }

    @Test
    fun itAddsTermToProjectGroupTerms() {

        val fakeProjectKey = "fake-project-key"

        val fakeTermUuid: UUID = UUID.randomUUID()
        whenever( mockUuidGenerator.generate() ).thenReturn( fakeTermUuid )

        val fakeTimestamp: Instant = Instant.now()
        whenever( mockTimestampGenerator.generate() ).thenReturn( fakeTimestamp )

        val fakeTerm = "fake-name"

        val fakeProject = Project( fakeProjectKey )
        whenever( mockProjectGroupRepository.findByProjectKey( any() ) ).thenReturn( fakeProject )

        val term = subject.execute( fakeProjectKey, fakeTerm )

        val expectedTerm = Term( fakeTermUuid, fakeTerm, fakeProjectKey )
        assertThat( term ).isEqualTo( expectedTerm )

        verify( mockUuidGenerator ).generate()
        verify( mockTimestampGenerator ).generate()
        verify( mockProjectGroupRepository ).save( fakeProject )

    }

}