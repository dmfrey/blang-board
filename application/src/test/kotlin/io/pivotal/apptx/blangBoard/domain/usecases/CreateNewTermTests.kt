package io.pivotal.apptx.blangBoard.domain.usecases

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.pivotal.apptx.blangBoard.domain.Project
import io.pivotal.apptx.blangBoard.domain.common.TimestampGenerator
import io.pivotal.apptx.blangBoard.domain.common.UuidGenerator
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.requests.CreateTermRequest
import io.pivotal.apptx.blangBoard.domain.usecases.responses.TermCreatedResponse
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

        val fakeTermUuid: UUID = UUID.randomUUID()
        whenever( mockUuidGenerator.generate() ).thenReturn( fakeTermUuid )

        val fakeTimestamp: Instant = Instant.now()
        whenever( mockTimestampGenerator.generate() ).thenReturn( fakeTimestamp )

        val fakeTerm = "fake-name"
        val fakeProjectKey = "fake-project-key"

        val fakeProject = Project( fakeProjectKey )
        whenever( mockProjectGroupRepository.findByProjectKey( fakeProjectKey ) ).thenReturn( fakeProject )

        val fakeCreateTermRequest = CreateTermRequest( fakeProjectKey, fakeTerm)
        val termCreatedResponse = subject.execute( fakeCreateTermRequest )

        val expectedTermCreatedResponse = TermCreatedResponse( fakeTermUuid, fakeTerm, fakeTimestamp, fakeProjectKey )
        assertThat( termCreatedResponse ).isEqualTo( expectedTermCreatedResponse )

        verify( mockUuidGenerator ).generate()
        verify( mockTimestampGenerator ).generate()
        verify( mockProjectGroupRepository ).save( fakeProject )

    }

}