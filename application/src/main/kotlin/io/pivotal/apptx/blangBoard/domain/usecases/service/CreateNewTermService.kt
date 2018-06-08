package io.pivotal.apptx.blangBoard.domain.usecases.service

import io.pivotal.apptx.blangBoard.domain.common.TimestampGenerator
import io.pivotal.apptx.blangBoard.domain.common.UuidGenerator
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.CreateNewTerm
import io.pivotal.apptx.blangBoard.domain.usecases.requests.CreateTermRequest
import io.pivotal.apptx.blangBoard.domain.usecases.responses.TermCreatedResponse
import org.springframework.stereotype.Component

@Component
class CreateNewTermService constructor(
        var projectRepository: ProjectRepository,
        var uuidGenerator: UuidGenerator,
        var timestampGenerator: TimestampGenerator ): CreateNewTerm {

    override fun execute( createTermRequest: CreateTermRequest ): TermCreatedResponse {

        val projectKey = createTermRequest.projectKey.toLowerCase().replace( ' ', '-' )
        val project = projectRepository.findByProjectKey( projectKey )

        val termUuid = uuidGenerator.generate()
        val occurredOn = timestampGenerator.generate()
        project.addTerm( termUuid, createTermRequest.term, occurredOn )

        projectRepository.save( project )

        return TermCreatedResponse( termUuid, createTermRequest.term, occurredOn, project.key )
    }

}
