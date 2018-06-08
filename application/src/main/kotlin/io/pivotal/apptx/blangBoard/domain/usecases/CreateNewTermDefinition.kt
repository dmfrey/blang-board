package io.pivotal.apptx.blangBoard.domain.usecases

import io.pivotal.apptx.blangBoard.domain.usecases.requests.CreateTermDefinitionRequest
import io.pivotal.apptx.blangBoard.domain.usecases.responses.TermDefinitionCreatedResponse

interface CreateNewTermDefinition {

    fun execute( createTermDefinitionRequest: CreateTermDefinitionRequest): TermDefinitionCreatedResponse

}
