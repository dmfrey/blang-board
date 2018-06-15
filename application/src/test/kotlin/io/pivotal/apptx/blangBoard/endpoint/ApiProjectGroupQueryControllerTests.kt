package io.pivotal.apptx.blangBoard.endpoint

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.pivotal.apptx.blangBoard.config.HalConfig
import io.pivotal.apptx.blangBoard.domain.Project
import io.pivotal.apptx.blangBoard.domain.usecases.LookupProjectGroup
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
@WebMvcTest( ApiProjectGroupQueryController::class )
@Import( HalConfig::class )
@AutoConfigureRestDocs( outputDir = "target/generated-snippets", uriScheme = "https", uriHost = "blang-board", uriPort = 443 )
class ApiProjectGroupQueryControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var mockLookupProjectGroup: LookupProjectGroup

    @Test
    fun aProjectGroupIsReturned() {

        val projectKey = "fake-project-key"

        val project = generateProject( projectKey )
        whenever( mockLookupProjectGroup.execute( any() ) ).thenReturn( project )

        val content = "{\"projectKey\":\"fake-project-key\",\"_links\":{\"self\":{\"href\":\"https://blang-board/api/projectGroups/fake-project-key\"},\"terms\":{\"href\":\"https://blang-board/api/projectGroups/fake-project-key/terms\",\"title\":\"terms\"}}}"

        mvc.perform( RestDocumentationRequestBuilders.get( "/api/projectGroups/{projectKey}", projectKey ) )
                .andDo( print() )
                .andExpect( status().is2xxSuccessful )
                .andExpect( content().contentType( "application/hal+json;charset=UTF-8" ) )
                .andExpect( content().json( content ) )
                .andDo(
                        document(   "get-project-group",
                                preprocessResponse( prettyPrint() ),
                                pathParameters(
                                        parameterWithName( "projectKey" ).description( "Key that identifies a group of projects" )
                                ),
                                responseFields(
                                        fieldWithPath( "projectKey" ).description("The Terms" ),
                                        subsectionWithPath("_links").description("The Hypermedia Links" )
                                ),
                                links( halLinks(),
                                        linkWithRel( "self" ).description("Link to self." ),
                                        linkWithRel( "terms" ).description("Link to terms resources." )
                                )
                        )
                )

        verify( mockLookupProjectGroup ).execute( projectKey )

    }

    private fun generateProject( projectKey: String ): Project {

        val project = Project( projectKey )

        val termUuid = UUID.randomUUID()
        project.addTerm( termUuid, "fake-term", Instant.now() )
        project.addTermDefinition( UUID.randomUUID(), "fake-term-definition", "fake-team-key", termUuid, Instant.now() )

        return project
    }

}