package io.pivotal.apptx.blangBoard.domain.usecases

import io.pivotal.apptx.blangBoard.domain.Term

interface LookupProjectGroupTerms {

    fun execute( projectKey: String ): List<Term>

}
