package io.pivotal.apptx.blangBoard.domain.persistence.integration

import com.fasterxml.jackson.databind.ObjectMapper
import io.pivotal.apptx.blangBoard.domain.Project
import io.pivotal.apptx.blangBoard.domain.common.UuidGenerator
import io.pivotal.apptx.blangBoard.domain.events.DomainEvent
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.persistence.integration.entities.DomainEventEntity
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp

@Component
@Transactional( readOnly = true )
class JpaProjectRepository constructor( var repository: DomainEventRepository, var uuidGenerator: UuidGenerator, var mapper: ObjectMapper ): ProjectRepository {

    @Transactional
    override fun save( project: Project ) {

        val currentChanges = findByProjectKey( project.key ).changes

        project.changes
                .filter { entry -> !currentChanges.containsKey( entry.key ) }
                .map { entry -> DomainEventEntity( entry.key.toString(), Timestamp.from( entry.value.occurredOn ), mapper.writeValueAsString( entry.value ), entry.value.key ) }
                .forEach { repository.save( it ) }

    }

    override fun findByProjectKey( key: String ): Project {

        val domainEventEntities = repository.findAllByProjectKey( key )
        val domainEvents: List<DomainEvent> = domainEventEntities
                .map { mapper.readValue( it.data, DomainEvent::class.java ) }

        return Project.createFrom( key, domainEvents )
    }

}