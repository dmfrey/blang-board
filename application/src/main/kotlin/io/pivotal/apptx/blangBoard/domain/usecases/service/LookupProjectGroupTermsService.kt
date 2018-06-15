package io.pivotal.apptx.blangBoard.domain.usecases.service

import io.pivotal.apptx.blangBoard.domain.Term
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.LookupProjectGroupTerms
import org.springframework.stereotype.Component
import java.util.*

@Component
class LookupProjectGroupTermsService constructor(
        val projectRepository: ProjectRepository
): LookupProjectGroupTerms {

    override fun execute( projectKey: String ): Map<UUID, Term> {

        return projectRepository.findByProjectKey( projectKey ).terms
    }

}