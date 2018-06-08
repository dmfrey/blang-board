package io.pivotal.apptx.blangBoard.domain.usecases.requests

import java.util.*

data class CreateTermDefinitionRequest(
        var projectKey: String,
        var termUuid: UUID,
        var teamKey: String,
        var definition: String
)
