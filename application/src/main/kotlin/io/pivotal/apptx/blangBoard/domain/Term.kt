package io.pivotal.apptx.blangBoard.domain

import java.util.*

data class Term(
        var name: String,
        var definitions: MutableMap<UUID, Definition> = mutableMapOf()
) {

    fun addDefinition( definitionUuid: UUID, definition: Definition ) {

        definitions[ definitionUuid ] = definition
    }

}