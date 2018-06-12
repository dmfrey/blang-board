package io.pivotal.apptx.blangBoard.domain

import java.util.*

data class Definition(
        val definitionUuid: UUID,
        val definition: String,
        val termUuid: UUID,
        val teamKey: String,
        val projectKey: String
)