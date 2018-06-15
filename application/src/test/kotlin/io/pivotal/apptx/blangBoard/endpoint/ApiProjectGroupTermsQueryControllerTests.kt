package io.pivotal.apptx.blangBoard.endpoint

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.pivotal.apptx.blangBoard.config.HalConfig
import io.pivotal.apptx.blangBoard.domain.Project
import io.pivotal.apptx.blangBoard.domain.usecases.LookupProjectGroupTerms
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.restdocs.hypermedia.HypermediaDocumentation.*
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant
import java.util.*

@RunWith( SpringRunner::class )
@WebMvcTest( ApiProjectGroupTermsQueryController::class )
@Import( HalConfig::class )
@AutoConfigureRestDocs( outputDir = "target/generated-snippets", uriScheme = "https", uriHost = "blang-board", uriPort = 443 )
class ApiProjectGroupTermsQueryControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var mockLookupProjectGroupTerms: LookupProjectGroupTerms

    val termUuid = UUID.fromString( "59eed85e-acce-49df-bef9-ccc45621bdfc" )
    val termName = "fake-term"
    val termOccurredOn = Instant.parse( "2018-06-12T20:15:00.000Z" )

    val definitionUuid = UUID.fromString( "0d711d7c-bdc5-4fe7-9aa2-7dc8eac002ce" )
    val definition = "fake-term-definition"
    val definitionTeamKey = "fake-team-key"
    val definitionOccurredOn = Instant.parse( "2018-06-12T20:15:00.000Z" )

    @Test
    fun aProjectGroupTermsIsReturned() {

        val projectKey = "fake-project-key"

        val project = generateProject( projectKey )
        val terms = project.terms
        whenever( mockLookupProjectGroupTerms.execute( any() ) ).thenReturn( terms )

        val content = "{\"terms\":[{\"termUuid\":\"59eed85e-acce-49df-bef9-ccc45621bdfc\",\"name\":\"fake-term\"}],\"_links\":{\"self\":{\"href\":\"https://blang-board/api/projectGroups/fake-project-key/terms\"},\"definitions\":{\"href\":\"https://blang-board/api/projectGroups/fake-project-key/terms/:termUuid/definitions\",\"title\":\"definitions\"},\"parent\":{\"href\":\"https://blang-board/api/projectGroups/fake-project-key\",\"title\":\"parent\"}}}"

        mvc.perform( RestDocumentationRequestBuilders.get( "/api/projectGroups/{projectKey}/terms", projectKey ) )
                .andDo( print() )
                .andExpect( status().is2xxSuccessful )
                .andExpect( content().contentType( "application/hal+json;charset=UTF-8" ) )
                .andExpect( content().json( content ) )
                .andDo(
                        document(   "get-project-group-terms",
                                preprocessResponse( prettyPrint() ),
                                pathParameters(
                                        parameterWithName( "projectKey" ).description( "Key that identifies a group of projects" )
                                ),
                                responseFields( //beneathPath( "terms" ),
                                        subsectionWithPath( "terms" ).description( "The Terms" ),
                                        fieldWithPath( "terms[].termUuid" ).description( "The UUID for the Term" ),
                                        fieldWithPath( "terms[].name" ).description( "The Term" ),
                                        fieldWithPath( "terms[].definitions[]" ).description( "The Definitions for the Term" ),
                                        fieldWithPath( "terms[].definitions[].definitionUuid" ).description( "The UUID for the Term" ),
                                        fieldWithPath( "terms[].definitions[].definition" ).description( "The Term" ),
                                        fieldWithPath( "terms[].definitions[].teamKey" ).description( "The Definitions for the Term" ),
                                        subsectionWithPath( "_links" ).description( "The Hypermedia Links" )
                                ),
                                links( halLinks(),
                                        linkWithRel("self" ).description("Link to self resource." ),
                                        linkWithRel( "definitions" ).description( "Link to parameterized definition list" ),
                                        linkWithRel("parent").description("Link to parent resource." )
                                )
                        )
                )

        verify( mockLookupProjectGroupTerms ).execute( projectKey )

    }

    private fun generateProject( projectKey: String ): Project {

        val project = Project( projectKey )

        project.addTerm( termUuid, termName, termOccurredOn )

        project.addTermDefinition( definitionUuid, definition, definitionTeamKey, termUuid, definitionOccurredOn )

        return project
    }

}