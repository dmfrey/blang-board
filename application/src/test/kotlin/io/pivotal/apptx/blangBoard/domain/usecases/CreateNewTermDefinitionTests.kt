package io.pivotal.apptx.blangBoard.domain.usecases

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.pivotal.apptx.blangBoard.domain.Definition
import io.pivotal.apptx.blangBoard.domain.Project
import io.pivotal.apptx.blangBoard.domain.common.TimestampGenerator
import io.pivotal.apptx.blangBoard.domain.common.UuidGenerator
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.service.CreateNewTermDefinitionService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.util.*

class CreateNewTermDefinitionTests {

    lateinit var subject: CreateNewTermDefinition

    lateinit var mockProjectGroupRepository: ProjectRepository
    lateinit var mockUuidGenerator: UuidGenerator
    lateinit var mockTimestampGenerator: TimestampGenerator

    @Before
    fun setup() {

        mockProjectGroupRepository = mock()
        mockUuidGenerator = mock()
        mockTimestampGenerator = mock()

        subject = CreateNewTermDefinitionService( mockProjectGroupRepository, mockUuidGenerator, mockTimestampGenerator )

    }

    @Test
    fun itAddsDefinitionToTerm() {

        val fakeProjectKey = "fake-project-key"

        val fakeTermUuid: UUID = UUID.randomUUID()
        val fakeTermDefinitionUuid: UUID = UUID.randomUUID()
        whenever( mockUuidGenerator.generate() ).thenReturn( fakeTermDefinitionUuid )

        val fakeTimestamp: Instant = Instant.now()
        whenever( mockTimestampGenerator.generate() ).thenReturn( fakeTimestamp )

        val fakeTeamKey = "fake-team-key"
        val fakeTermDefinition = "fake-name-definition"

        val fakeProject = Project( fakeProjectKey )
        whenever( mockProjectGroupRepository.findByProjectKey( fakeProjectKey ) ).thenReturn( fakeProject )

        val definition = subject.execute( fakeProjectKey, fakeTeamKey, fakeTermUuid, fakeTermDefinition )

        val expectedDefinition = Definition( fakeTermDefinitionUuid, fakeTermDefinition, fakeTermUuid, fakeTeamKey, fakeProjectKey )
        assertThat( definition ).isEqualTo( expectedDefinition )

        verify( mockUuidGenerator ).generate()
        verify( mockTimestampGenerator ).generate()
        verify( mockProjectGroupRepository ).save( fakeProject )

    }

}