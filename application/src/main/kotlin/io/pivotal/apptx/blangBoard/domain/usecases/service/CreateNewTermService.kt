package io.pivotal.apptx.blangBoard.domain.usecases.service

import io.pivotal.apptx.blangBoard.domain.Term
import io.pivotal.apptx.blangBoard.domain.common.TimestampGenerator
import io.pivotal.apptx.blangBoard.domain.common.UuidGenerator
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.CreateNewTerm
import org.springframework.stereotype.Component

@Component
class CreateNewTermService constructor(
        var projectRepository: ProjectRepository,
        var uuidGenerator: UuidGenerator,
        var timestampGenerator: TimestampGenerator ): CreateNewTerm {

    override fun execute( projectKey: String, term: String ): Term {

        val projectKeyFormatted = projectKey.toLowerCase().replace( ' ', '-' )
        val project = projectRepository.findByProjectKey( projectKeyFormatted )

        val termUuid = uuidGenerator.generate()
        val occurredOn = timestampGenerator.generate()
        project.addTerm( termUuid, term, occurredOn )

        projectRepository.save( project )

        return Term( termUuid, term, project.key )
    }

}
