package io.pivotal.apptx.blangBoard.endpoint.model

import io.pivotal.apptx.blangBoard.domain.Project
import org.springframework.hateoas.ResourceSupport

data class ProjectResource(
        var projectGroup: String
): ResourceSupport() {

    companion object {

        fun createFrom( project: Project ): ProjectResource {

            var projectGroupModel = ProjectResource( project.key )

            return projectGroupModel
        }
    }

}