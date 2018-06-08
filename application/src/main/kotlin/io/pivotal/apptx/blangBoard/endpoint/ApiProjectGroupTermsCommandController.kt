package io.pivotal.apptx.blangBoard.endpoint

import io.pivotal.apptx.blangBoard.domain.usecases.CreateNewTerm
import io.pivotal.apptx.blangBoard.domain.usecases.requests.CreateTermRequest
import io.pivotal.apptx.blangBoard.endpoint.model.NewTermRequestModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
class ApiProjectGroupTermsCommandController constructor( var createNewTerm: CreateNewTerm ) {

    @PostMapping( "/api/projectGroups/{projectGroup}/terms" )
    fun createNewTerm(
            @PathVariable( "projectGroup" ) projectGroup: String,
            @Valid @RequestBody newTermRequest : NewTermRequestModel,
            uriComponentsBuilder: UriComponentsBuilder ): ResponseEntity<Void> {

        val termCreatedResponse = createNewTerm.execute( CreateTermRequest( projectGroup, newTermRequest.name ) )

        return ResponseEntity
                .created( uriComponentsBuilder.path( "/api/projectGroups/{projectKey}/terms/{termUuid}" ).buildAndExpand( termCreatedResponse.projectKey, termCreatedResponse.termUuid ).toUri() )
                .build()
    }

}