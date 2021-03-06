package io.pivotal.apptx.blangBoard.domain.usecases

import io.pivotal.apptx.blangBoard.domain.Term
import java.util.*

interface LookupProjectGroupTerms {

    fun execute( projectKey: String ): Map<UUID, Term>

}
