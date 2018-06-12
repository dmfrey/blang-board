package io.pivotal.apptx.blangBoard.domain.usecases

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.pivotal.apptx.blangBoard.domain.Project
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.service.LookupProjectGroupService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class LookupProjectGroupTests {

    lateinit var subject: LookupProjectGroup

    lateinit var mockProjectGroupRepository: ProjectRepository

    @Before
    fun setup() {

        mockProjectGroupRepository = mock()

        subject = LookupProjectGroupService( mockProjectGroupRepository )

    }

    @Test
    fun itFindsAnExistingProjectGroup() {

        val fakeProjectKey = "fake-project-key"

        val fakeProject = Project( fakeProjectKey )
        whenever( mockProjectGroupRepository.findByProjectKey( any() ) ).thenReturn( fakeProject )

        val project = subject.execute( fakeProjectKey )

        val expectedProject = Project( fakeProjectKey )
        assertThat( project ).isEqualTo( expectedProject )

        verify( mockProjectGroupRepository ).findByProjectKey( fakeProjectKey )

    }

}