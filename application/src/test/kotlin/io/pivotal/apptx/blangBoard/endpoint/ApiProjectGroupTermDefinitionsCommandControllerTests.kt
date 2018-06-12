package io.pivotal.apptx.blangBoard.endpoint

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.pivotal.apptx.blangBoard.domain.Definition
import io.pivotal.apptx.blangBoard.domain.usecases.CreateNewTermDefinition
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@RunWith( SpringRunner::class )
@WebMvcTest( ApiProjectGroupTermDefinitionsCommandController::class )
@AutoConfigureRestDocs( outputDir = "target/generated-snippets", uriScheme = "https", uriHost = "blang-board", uriPort = 443 )
class ApiProjectGroupTermDefinitionsCommandControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var mockCreateNewTermDefinition: CreateNewTermDefinition

    @Test
    fun aNewTermDefinitionIsRecordedForATeamInAProjectGroup() {

        val projectKey = "fake-project-key"
        val teamKey = "fake-team-key"
        val termUuid = UUID.randomUUID()
        val termDefinitionUuid = UUID.randomUUID()

        val definition = Definition( termDefinitionUuid, "fake-term-definition", termUuid, teamKey, projectKey )
        whenever( mockCreateNewTermDefinition.execute( any(), any(), any(), any() ) ).thenReturn( definition )

        val requestBody = "{\"teamKey\": \"team-key\", \"definition\": \"a fake definition for a fake term\"}"

        mvc.perform(
                RestDocumentationRequestBuilders.post( "/api/projectGroups/{projectKey}/terms/{termUuid}/definitions", projectKey, termUuid )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( requestBody )
        )
                .andDo( print() )
                .andExpect( status().is2xxSuccessful )
                .andExpect( header().string( HttpHeaders.LOCATION, "https://blang-board/api/projectGroups/${definition.projectKey}/terms/${definition.termUuid}/definitions/${definition.definitionUuid}" ) )
                .andDo(
                        document("create-term-definition",
                                preprocessResponse( prettyPrint() ),
                                pathParameters(
                                        parameterWithName("projectKey" ).description("Key that identifies a group of projects" ),
                                        parameterWithName("termUuid" ).description("The unique Id of Term the Definition is related to" )
                                ),
                                requestFields(
                                        fieldWithPath( "teamKey" ).description( "The Term that defined this Definition" ),
                                        fieldWithPath( "definition" ).description( "The Definition of the Term as it relates to the Team" )
                                )
                        )
                )

    }

}