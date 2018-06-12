package io.pivotal.apptx.blangBoard.endpoint

import io.pivotal.apptx.blangBoard.domain.usecases.LookupProjectGroup
import io.pivotal.apptx.blangBoard.endpoint.model.ProjectResource
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
class ApiProjectGroupQueryController constructor( var lookupProjectGroup: LookupProjectGroup ) {

    @GetMapping( "/api/projectGroups/{projectGroup}" )
    fun lookupProjectGroup(
            @PathVariable( "projectGroup" ) projectGroup: String,
            uriComponentsBuilder: UriComponentsBuilder ): ResponseEntity<ProjectResource> {

        val projectResource = ProjectResource.createFrom( lookupProjectGroup.execute( projectGroup ) )
        projectResource
                .add(
                        linkTo( methodOn( ApiProjectGroupQueryController::class.java ).lookupProjectGroup( projectGroup, uriComponentsBuilder ) )
                                .withSelfRel()
                )
        projectResource
                .add(
                        linkTo( ApiProjectGroupTermsCommandController::class.java ).slash( "api" ).slash( "projectGroups" ).slash( projectGroup ).slash( "terms" )
                                .withRel( "terms" )
                                .withTitle( "terms" )
                )

        return ResponseEntity
                .ok( projectResource )
    }

}
