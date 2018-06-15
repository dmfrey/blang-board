package io.pivotal.apptx.blangBoard.domain.usecases

import java.util.*

interface CreateNewTermDefinition {

    fun execute( projectKey: String, teamKey: String, termUuid: UUID, definition: String ): UUID

}
