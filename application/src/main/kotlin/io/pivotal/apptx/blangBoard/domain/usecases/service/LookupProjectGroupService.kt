package io.pivotal.apptx.blangBoard.domain.usecases.service

import io.pivotal.apptx.blangBoard.domain.Project
import io.pivotal.apptx.blangBoard.domain.persistence.ProjectRepository
import io.pivotal.apptx.blangBoard.domain.usecases.LookupProjectGroup
import org.springframework.stereotype.Component

@Component
class LookupProjectGroupService constructor(
        val projectRepository: ProjectRepository ): LookupProjectGroup {

    override fun execute( projectKey: String ): Project {

        return projectRepository.findByProjectKey( projectKey )
    }

}