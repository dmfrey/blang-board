package io.pivotal.apptx.blangBoard.endpoint.model

import io.pivotal.apptx.blangBoard.domain.Term
import org.springframework.hateoas.ResourceSupport
import java.util.*

data class TermsResource(
        var terms: List<TermResource>
): ResourceSupport() {

    companion object {

        fun createFrom( terms: Map<UUID, Term> ): TermsResource {

            return TermsResource( terms.map { TermResource( it.key, it.value.name, it.value.definitions.map { DefinitionResource( it.key, it.value.definition, it.value.teamKey ) } ) } )
        }
    }

}
