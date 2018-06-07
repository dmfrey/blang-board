package io.pivotal.apptx.blangBoard.domain.persistence

import io.pivotal.apptx.blangBoard.domain.Project

interface ProjectRepository {

    fun save( project: Project )

    fun findByProjectKey( key: String ): Project

}