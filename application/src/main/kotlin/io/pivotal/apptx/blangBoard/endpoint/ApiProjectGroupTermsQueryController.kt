package io.pivotal.apptx.blangBoard.endpoint

import io.pivotal.apptx.blangBoard.domain.usecases.LookupProjectGroupTerms
import io.pivotal.apptx.blangBoard.endpoint.model.TermsResource
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
class ApiProjectGroupTermsQueryController constructor( var lookupProjectGroupTerms: LookupProjectGroupTerms ) {

    @GetMapping( "/api/projectGroups/{projectKey}/terms" )
    fun lookupProjectGroupTerms(
            @PathVariable( "projectKey" ) projectGroup: String,
            uriComponentsBuilder: UriComponentsBuilder ): ResponseEntity<TermsResource> {

        val termsResource = TermsResource.createFrom( lookupProjectGroupTerms.execute( projectGroup ) )
        termsResource
                .add(
                        linkTo( methodOn( ApiProjectGroupTermsQueryController::class.java ).lookupProjectGroupTerms( projectGroup, uriComponentsBuilder ) )
                                .withSelfRel()
                )
        termsResource
                .add(
                        linkTo( ApiProjectGroupTermsQueryController::class.java ).slash( "api" ).slash( "projectGroups" ).slash( projectGroup ).slash( "terms" ).slash( ":termUuid" ).slash( "definitions" )
                                .withRel( "definitions" )
                                .withTitle( "definitions" )
                )
        termsResource
                .add(
                        linkTo( methodOn( ApiProjectGroupQueryController::class.java ).lookupProjectGroup( projectGroup, uriComponentsBuilder ) )
                                .withRel( "parent" )
                                .withTitle( "parent" )
                )

        return ResponseEntity
                .ok( termsResource )
    }

}