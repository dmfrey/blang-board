package io.pivotal.apptx.blangBoard.domain.usecases

import java.util.*

interface CreateNewTerm {

    fun execute( projectKey: String, term: String ): UUID

}