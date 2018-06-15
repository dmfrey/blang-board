package io.pivotal.apptx.blangBoard.endpoint.model

import java.util.*

data class TermResource(
        val termUuid: UUID,
        val name: String,
        val definitions: List<DefinitionResource>
)