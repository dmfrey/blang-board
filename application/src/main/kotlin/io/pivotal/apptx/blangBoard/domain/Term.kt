package io.pivotal.apptx.blangBoard.domain

import java.util.*

data class Term(
        var termUuid: UUID,
        var name: String,
        var projectKey: String
)