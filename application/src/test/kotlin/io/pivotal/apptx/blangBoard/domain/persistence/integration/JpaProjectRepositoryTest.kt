package io.pivotal.apptx.blangBoard.domain.persistence.integration

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.nhaarman.mockito_kotlin.*
import io.pivotal.apptx.blangBoard.domain.Project
import io.pivotal.apptx.blangBoard.domain.Term
import io.pivotal.apptx.blangBoard.domain.common.UuidGenerator
import io.pivotal.apptx.blangBoard.domain.events.TermCreated
import io.pivotal.apptx.blangBoard.domain.persistence.integration.entities.DomainEventEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.sql.Timestamp
import java.time.Instant
import java.util.*

class JpaProjectRepositoryTest() {

    lateinit var subject: JpaProjectRepository

    lateinit var domainEventRepository: DomainEventRepository
    lateinit var uuidGenerator: UuidGenerator

    var mapper = jacksonObjectMapper()

    @Before
    fun setup() {

        mapper.registerKotlinModule()
        mapper.registerModule( Jdk8Module() )
        mapper.registerModule( JavaTimeModule() )
        mapper.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false )
        mapper.configure( SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false )

        domainEventRepository = mock()
        uuidGenerator = mock()

        subject = JpaProjectRepository( domainEventRepository, uuidGenerator, mapper )

    }

    @Test
    fun itReadsDomainEventsAndReturnsProject() {

        val projectKey = "fake-project-key"

        val termUuid = UUID.randomUUID()
        val name = "fake-term"
        val occurredOn = Instant.now()
        val termCreated = TermCreated( termUuid, name, occurredOn, projectKey )

        val fakeDomainEventEntity = DomainEventEntity( termUuid.toString(), Timestamp.from( occurredOn ), mapper.writeValueAsString( termCreated ), projectKey )
        whenever( domainEventRepository.findAllByProjectKey( any() ) ).thenReturn( listOf( fakeDomainEventEntity ) )

        val project  = subject.findByProjectKey( projectKey )

        val expectedTerm = Term( termUuid, name )

        assertThat( project ).isNotNull
        assertThat( project.key ).isEqualTo( projectKey )
        assertThat( project.terms )
                .hasSize( 1 )
                .containsKey( termUuid )
                .containsValue( expectedTerm )
        assertThat( project.changes ).hasSize( 1 )

        verify( domainEventRepository ).findAllByProjectKey( projectKey )

    }

    @Test
    fun itReturnsNewProjectWhenNoEventsExist() {

        val projectKey = "fake-project-key"

        whenever( domainEventRepository.findAllByProjectKey( any() ) ).thenReturn( listOf() )

        val project  = subject.findByProjectKey( projectKey )

        assertThat( project ).isNotNull
        assertThat( project.key ).isEqualTo( projectKey )
        assertThat( project.terms ).isEmpty()
        assertThat( project.changes ).isEmpty()

        verify( domainEventRepository ).findAllByProjectKey( projectKey )

    }

    @Test
    fun itSavesDomainEventsAndReturnsProject() {

        val projectKey = "fake-project-key"

        val termUuid = UUID.randomUUID()
        val name = "fake-term"

        whenever( domainEventRepository.findAllByProjectKey( any() ) ).thenReturn( listOf() )

        val project = Project( projectKey )

        val occurredOn = Instant.now()
        project.addTerm( termUuid, name, occurredOn )
        subject.save( project )

        val expectedTermCreated = TermCreated( termUuid, name, occurredOn, projectKey )
        val expectedDomainEventEntity = DomainEventEntity( termUuid.toString(), Timestamp.from( occurredOn ), mapper.writeValueAsString( expectedTermCreated ), projectKey )

        verify( domainEventRepository ).findAllByProjectKey( projectKey )
        verify( domainEventRepository ).save( expectedDomainEventEntity )

    }

    @Test
    fun itSavesDomainEventsWithExistingDomainEventsAndReturnsProject() {

        val projectKey = "fake-project-key"

        val termUuid1 = UUID.randomUUID()
        val name1 = "fake-term-1"
        val occurredOn1 = Instant.now()

        val firstTermCreated = TermCreated( termUuid1, name1, occurredOn1, projectKey )
        val firstDomainEventEntity = DomainEventEntity( termUuid1.toString(), Timestamp.from( occurredOn1 ), mapper.writeValueAsString( firstTermCreated ), projectKey )

        whenever( domainEventRepository.findAllByProjectKey( any() ) ).thenReturn( listOf( firstDomainEventEntity ) )

        val project = subject.findByProjectKey( projectKey )

        val termUuid2 = UUID.randomUUID()
        val name2 = "fake-term-2"
        val occurredOn2 = Instant.now()
        project.addTerm( termUuid2, name2, occurredOn2 )
        subject.save( project )

        val expectedTermCreated = TermCreated( termUuid2, name2, occurredOn2, projectKey )
        val expectedDomainEventEntity = DomainEventEntity( termUuid2.toString(), Timestamp.from( occurredOn2 ), mapper.writeValueAsString( expectedTermCreated ), projectKey )

        verify( domainEventRepository, times( 2 ) ).findAllByProjectKey( projectKey )
        verify( domainEventRepository ).save( expectedDomainEventEntity )

    }

}