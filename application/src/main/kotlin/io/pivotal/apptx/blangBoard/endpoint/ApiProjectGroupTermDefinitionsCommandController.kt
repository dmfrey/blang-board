package io.pivotal.apptx.blangBoard.endpoint

import io.pivotal.apptx.blangBoard.domain.usecases.CreateNewTermDefinition
import io.pivotal.apptx.blangBoard.endpoint.model.NewTermDefinitionRequestModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.validation.Valid

@RestController
class ApiProjectGroupTermDefinitionsCommandController constructor( var createNewTermDefinition: CreateNewTermDefinition ) {

    @PostMapping( "/api/projectGroups/{projectGroup}/terms/{termUuid}/definitions" )
    fun createNewTermDefinition(
            @PathVariable( "projectGroup" ) projectGroup: String,
            @PathVariable( "termUuid" ) termUuid: UUID,
            @Valid @RequestBody newTermDefinitionRequest : NewTermDefinitionRequestModel,
            uriComponentsBuilder: UriComponentsBuilder ): ResponseEntity<Void> {

        val definition = createNewTermDefinition.execute( projectGroup, newTermDefinitionRequest.teamKey, termUuid, newTermDefinitionRequest.definition )

        return ResponseEntity
                .created( uriComponentsBuilder.path( "/api/projectGroups/{projectKey}/terms/{termUuid}/definitions/{definitionUuid}" ).buildAndExpand( definition.projectKey, definition.termUuid, definition.definitionUuid ).toUri() )
                .build()
    }

}