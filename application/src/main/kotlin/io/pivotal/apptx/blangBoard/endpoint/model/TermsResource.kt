package io.pivotal.apptx.blangBoard.endpoint.model

import io.pivotal.apptx.blangBoard.domain.Term
import org.springframework.hateoas.ResourceSupport

data class TermsResource(
        var terms: List<TermModel>
): ResourceSupport() {

    companion object {

        fun createFrom( terms: List<Term> ): TermsResource {

            return TermsResource( terms.map { TermModel( it.termUuid, it.name ) } )
        }
    }

}
