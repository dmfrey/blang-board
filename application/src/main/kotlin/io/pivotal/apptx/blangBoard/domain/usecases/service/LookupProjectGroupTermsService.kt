package io.pivotal.apptx.blangBoard.domain.usecases.service

import io.pivotal.apptx.blangBoard.domain.Term
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.LookupProjectGroupTerms
import org.springframework.stereotype.Component

@Component
class LookupProjectGroupTermsService constructor(
        val projectRepository: ProjectRepository
): LookupProjectGroupTerms {

    override fun execute( projectKey: String ): List<Term> {

        return projectRepository.findByProjectKey( projectKey ).terms
                .map { it.value }
    }

}