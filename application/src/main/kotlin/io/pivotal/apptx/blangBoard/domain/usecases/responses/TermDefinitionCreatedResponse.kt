package io.pivotal.apptx.blangBoard.domain.usecases.responses

import java.time.Instant
import java.util.*

data class TermDefinitionCreatedResponse(
        var termDefinitionUuid: UUID,
        var definition: String,
        var occurredOn: Instant,
        var termUuid: UUID,
        var projectKey: String
)
