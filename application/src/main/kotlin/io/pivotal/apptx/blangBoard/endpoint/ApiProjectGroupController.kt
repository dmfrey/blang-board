package io.pivotal.apptx.blangBoard.endpoint

import io.pivotal.apptx.blangBoard.domain.usecases.CreateNewTerm
import io.pivotal.apptx.blangBoard.domain.usecases.CreateNewTermDefinition
import io.pivotal.apptx.blangBoard.domain.usecases.requests.CreateTermDefinitionRequest
import io.pivotal.apptx.blangBoard.domain.usecases.requests.CreateTermRequest
import io.pivotal.apptx.blangBoard.endpoint.model.NewTermDefinitionRequestModel
import io.pivotal.apptx.blangBoard.endpoint.model.NewTermRequestModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping( "/api/projectGroups" )
class ApiProjectGroupController constructor(
        var createNewTerm: CreateNewTerm,
        var createNewTermDefinition: CreateNewTermDefinition ) {

    @PostMapping( "/{projectGroup}/terms" )
    fun createNewTerm(
            @PathVariable( "projectGroup" ) projectGroup: String,
            @Valid @RequestBody newTermRequest : NewTermRequestModel,
            uriComponentsBuilder: UriComponentsBuilder ): ResponseEntity<Void> {

        val termCreatedResponse = createNewTerm.execute( CreateTermRequest( projectGroup, newTermRequest.name ) )

        return ResponseEntity
                .created( uriComponentsBuilder.path( "/api/projectGroups/{projectKey}/terms/{termUuid}" ).buildAndExpand( termCreatedResponse.projectKey, termCreatedResponse.termUuid ).toUri() )
                .build()
    }

    @PostMapping( "/{projectGroup}/terms/{termUuid}" )
    fun createNewTermDefinition(
            @PathVariable( "projectGroup" ) projectGroup: String,
            @PathVariable( "termUuid" ) termUuid: UUID,
            @Valid @RequestBody newTermDefinitionRequest : NewTermDefinitionRequestModel,
            uriComponentsBuilder: UriComponentsBuilder ): ResponseEntity<Void> {

        val termDefinitionCreatedResponse = createNewTermDefinition.execute( CreateTermDefinitionRequest( projectGroup, termUuid, newTermDefinitionRequest.teamKey, newTermDefinitionRequest.definition ) )

        return ResponseEntity
                .created( uriComponentsBuilder.path( "/api/projectGroups/{projectKey}/terms/{termUuid}" ).buildAndExpand( termDefinitionCreatedResponse.projectKey, termDefinitionCreatedResponse.termUuid ).toUri() )
                .build()
    }

}