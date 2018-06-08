package io.pivotal.apptx.blangBoard.domain.usecases.service

import io.pivotal.apptx.blangBoard.domain.common.TimestampGenerator
import io.pivotal.apptx.blangBoard.domain.common.UuidGenerator
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.CreateNewTermDefinition
import io.pivotal.apptx.blangBoard.domain.usecases.requests.CreateTermDefinitionRequest
import io.pivotal.apptx.blangBoard.domain.usecases.responses.TermDefinitionCreatedResponse
import org.springframework.stereotype.Component

@Component
class CreateNewTermDefinitionService(
        var projectRepository: ProjectRepository,
        var uuidGenerator: UuidGenerator,
        var timestampGenerator: TimestampGenerator ): CreateNewTermDefinition {

    override fun execute( createTermDefinitionRequest: CreateTermDefinitionRequest ): TermDefinitionCreatedResponse {

        val teamKey = createTermDefinitionRequest.teamKey.toLowerCase().replace( ' ', '-' )
        val project = projectRepository.findByProjectKey( createTermDefinitionRequest.projectKey )

        val definitionUuid = uuidGenerator.generate()
        val occurredOn = timestampGenerator.generate()
        project.addTermDefinition(
                definitionUuid,
                createTermDefinitionRequest.definition,
                teamKey,
                createTermDefinitionRequest.termUuid,
                occurredOn )

        projectRepository.save( project )

        return TermDefinitionCreatedResponse( definitionUuid, createTermDefinitionRequest.definition, occurredOn, createTermDefinitionRequest.termUuid, createTermDefinitionRequest.projectKey )
    }

}