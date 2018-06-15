package io.pivotal.apptx.blangBoard.endpoint.model

import io.pivotal.apptx.blangBoard.domain.Definition
import org.springframework.hateoas.ResourceSupport
import java.util.*

data class DefinitionsResource(
        var definitions: List<DefinitionResource>
): ResourceSupport() {

    companion object {

        fun createFrom( terms: Map<UUID, Definition> ): DefinitionsResource {

            return DefinitionsResource( terms.map { DefinitionResource( it.key, it.value.definition, it.value.teamKey ) } )
        }
    }

}
