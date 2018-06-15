package io.pivotal.apptx.blangBoard.domain.usecases.service

import io.pivotal.apptx.blangBoard.domain.common.TimestampGenerator
import io.pivotal.apptx.blangBoard.domain.common.UuidGenerator
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.CreateNewTermDefinition
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreateNewTermDefinitionService(
        var projectRepository: ProjectRepository,
        var uuidGenerator: UuidGenerator,
        var timestampGenerator: TimestampGenerator ): CreateNewTermDefinition {

    override fun execute(projectKey: String, teamKey: String, termUuid: UUID, definition: String ): UUID {

        val teamKeyFormatted = teamKey.toLowerCase().replace( ' ', '-' )
        val project = projectRepository.findByProjectKey( projectKey )

        val definitionUuid = uuidGenerator.generate()
        val occurredOn = timestampGenerator.generate()
        project.addTermDefinition(
                definitionUuid,
                definition,
                teamKeyFormatted,
                termUuid,
                occurredOn )

        projectRepository.save( project )

        return definitionUuid
    }

}