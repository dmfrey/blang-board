package io.pivotal.apptx.blangBoard.domain.usecases

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.pivotal.apptx.blangBoard.domain.Project
import io.pivotal.apptx.blangBoard.domain.Term
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.service.LookupProjectGroupTermsService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.util.*

class LookupProjectGroupTermsTests {

    lateinit var subject: LookupProjectGroupTerms

    lateinit var mockProjectGroupRepository: ProjectRepository

    @Before
    fun setup() {

        mockProjectGroupRepository = mock()

        subject = LookupProjectGroupTermsService( mockProjectGroupRepository )

    }

    @Test
    fun itFindsAnExistingProjectGroup() {

        val fakeProjectKey = "fake-project-key"

        val fakeProject = generateProject( fakeProjectKey )
        whenever( mockProjectGroupRepository.findByProjectKey( any() ) ).thenReturn( fakeProject )

        val terms = subject.execute( fakeProjectKey )

        val termUuid = UUID.fromString( "59eed85e-acce-49df-bef9-ccc45621bdfc" )
        val expectedTerm = Term( termUuid, "fake-term", fakeProjectKey )

        val expectedTerms = listOf( expectedTerm )
        assertThat( terms ).isEqualTo( expectedTerms )

        verify( mockProjectGroupRepository ).findByProjectKey( fakeProjectKey )

    }

    private fun generateProject( projectKey: String ): Project {

        val project = Project( projectKey )

        val termUuid = UUID.fromString( "59eed85e-acce-49df-bef9-ccc45621bdfc" )
        val termOccurredOn = Instant.parse( "2018-06-12T20:15:00.000Z" )
        project.addTerm( termUuid, "fake-term", termOccurredOn )

        val definitionUuid = UUID.fromString( "0d711d7c-bdc5-4fe7-9aa2-7dc8eac002ce" )
        val definitionOccurredOn = Instant.parse( "2018-06-12T20:15:00.000Z" )
        project.addTermDefinition( definitionUuid, "fake-term-definition", "fake-team-key", termUuid, definitionOccurredOn )

        return project
    }

}