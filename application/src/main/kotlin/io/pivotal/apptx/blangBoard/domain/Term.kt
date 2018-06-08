package io.pivotal.apptx.blangBoard.domain

import java.util.*

data class Term(
        var uuid: UUID,
        var name: String,
        var definitions: MutableMap<UUID, Definition> = mutableMapOf()
)