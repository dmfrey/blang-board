package io.pivotal.apptx.blangBoard.domain.usecases

import io.pivotal.apptx.blangBoard.domain.Definition
import java.util.*

interface CreateNewTermDefinition {

    fun execute( projectKey: String, teamKey: String, termUuid: UUID, definition: String ): Definition

}
