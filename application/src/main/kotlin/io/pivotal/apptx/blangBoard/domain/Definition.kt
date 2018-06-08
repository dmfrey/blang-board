package io.pivotal.apptx.blangBoard.domain

import java.util.*

data class Definition(
        val definitionUuid: UUID,
        val definition: String,
        val teamKey: String,
        val termUuid: UUID
)