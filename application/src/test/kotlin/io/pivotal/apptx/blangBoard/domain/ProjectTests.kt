package io.pivotal.apptx.blangBoard.domain

import io.pivotal.apptx.blangBoard.domain.events.TermCreated
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.Instant
import java.util.*

class ProjectTests {

    @Test
    fun itCreatesNewProject() {

        val fakeProjectKey = "fake-project-key"

        val project = Project( fakeProjectKey )

        assertThat( project.key ).isEqualTo( fakeProjectKey )
        assertThat( project.terms ).isEmpty()
        assertThat( project.changes ).isEmpty()

    }

    @Test
    fun itCreatesNewProjectAndAddsNewTerm() {

        val fakeProjectKey = "fake-project-key"

        val project = Project( fakeProjectKey )

        val fakeTermUuid = UUID.randomUUID()
        val fakeTermName = "fake-name"

        val fakeOccurredOn: Instant = Instant.now()
        project.addTerm( fakeTermUuid, fakeTermName, fakeOccurredOn )

        val expectedTermCreated = TermCreated( fakeTermUuid, fakeTermName, fakeOccurredOn, fakeProjectKey )

        assertThat( project.key ).isEqualTo( fakeProjectKey )
        assertThat( project.terms ).hasSize( 1 )
        assertThat( project.changes )
                .hasSize( 1 )
                .containsKey( fakeTermUuid )
                .containsValue( expectedTermCreated )

    }

    @Test
    fun itRecreatesProjectFromDomainEvents() {

        val fakeProjectKey = "fake-project-key"

        val fakeTermUuid = UUID.randomUUID()
        val fakeTermName = "fake-name"
        val fakeOccurredOn = Instant.now()
        val fakeTermCreated = TermCreated( fakeTermUuid, fakeTermName, fakeOccurredOn, fakeProjectKey )

        val project = Project.createFrom( fakeProjectKey, mutableListOf( fakeTermCreated ) )

        val expectedTermCreated = TermCreated( fakeTermUuid, fakeTermName, fakeOccurredOn, fakeProjectKey )

        assertThat( project.key ).isEqualTo( fakeProjectKey )
        assertThat( project.terms ).hasSize( 1 )
        assertThat( project.changes )
                .hasSize( 1 )
                .containsKey( fakeTermUuid )
                .containsValue( expectedTermCreated )

    }

    @Test
    fun itFlushesDomainEvents() {

        val fakeProjectKey = "fake-project-key"

        val fakeTermUuid = UUID.randomUUID()
        val fakeTermName = "fake-name"
        val fakeOccurredOn = Instant.now()
        val fakeTermCreated = TermCreated( fakeTermUuid, fakeTermName, fakeOccurredOn, fakeProjectKey )

        val project = Project.createFrom( fakeProjectKey, mutableListOf( fakeTermCreated ) )
        project.flushChanges()

        assertThat( project.key ).isEqualTo( fakeProjectKey.toLowerCase().replace( ' ', '-' ) )
        assertThat( project.terms ).hasSize( 1 )
        assertThat( project.changes ).isEmpty()

    }

}