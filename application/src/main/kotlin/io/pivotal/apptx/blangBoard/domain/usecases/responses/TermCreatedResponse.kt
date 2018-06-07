package io.pivotal.apptx.blangBoard.domain.usecases.responses

import java.time.Instant
import java.util.*

data class TermCreatedResponse(
        var termUuid: UUID,
        var name: String,
        var dateCreated: Instant,
        var projectKey: String
)