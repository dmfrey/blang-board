package io.pivotal.apptx.blangBoard.domain.usecases

import io.pivotal.apptx.blangBoard.domain.usecases.requests.CreateTermRequest
import io.pivotal.apptx.blangBoard.domain.usecases.responses.TermCreatedResponse

interface CreateNewTerm {

    fun execute( createTermRequest: CreateTermRequest ): TermCreatedResponse

}