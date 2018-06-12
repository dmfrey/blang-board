package io.pivotal.apptx.blangBoard.domain.usecases

import io.pivotal.apptx.blangBoard.domain.Term

interface CreateNewTerm {

    fun execute( projectKey: String, term: String ): Term

}