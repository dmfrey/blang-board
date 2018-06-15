package io.pivotal.apptx.blangBoard.endpoint.model

import io.pivotal.apptx.blangBoard.domain.Definition
import java.util.*

data class DefinitionResource(
        val definitionUuid: UUID,
        val definition: String,
        val teamKey: String
) {

    companion object {

        fun createFrom( definitionUuid: UUID, definition: Definition ): DefinitionResource {

            return DefinitionResource( definitionUuid, definition.definition, definition.teamKey )
        }
    }

}