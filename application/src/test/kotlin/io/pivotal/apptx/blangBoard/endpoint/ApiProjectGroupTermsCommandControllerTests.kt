package io.pivotal.apptx.blangBoard.endpoint

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.pivotal.apptx.blangBoard.domain.usecases.CreateNewTerm
import io.pivotal.apptx.blangBoard.domain.usecases.responses.TermCreatedResponse
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant
import java.util.*

@RunWith( SpringRunner::class )
@WebMvcTest( ApiProjectGroupTermsCommandController::class )
@AutoConfigureRestDocs( outputDir = "target/generated-snippets", uriScheme = "https", uriHost = "blang-board", uriPort = 443 )
class ApiProjectGroupTermsCommandControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var mockCreateNewTerm: CreateNewTerm

    @Test
    fun aNewTermIsRecordedForAProjectGroup() {

        val projectKey = "fake-project-key"
        val termUuid = UUID.randomUUID()

        val termCreatedResponse = TermCreatedResponse( termUuid, "fake-term", Instant.now(), projectKey )
        whenever( mockCreateNewTerm.execute( any() ) ).thenReturn( termCreatedResponse )

        val requestBody = "{\"name\": \"new name\"}"

        mvc.perform(
                RestDocumentationRequestBuilders.post( "/api/projectGroups/{projectKey}/terms", projectKey )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( requestBody )
                        .header( HttpHeaders.LOCATION, "/api/projectGroups/${termCreatedResponse.projectKey}/terms/${termCreatedResponse.termUuid}" )
                )
                .andDo( print() )
                .andExpect( status().is2xxSuccessful )
                .andDo(
                        document("create-term",
                            preprocessResponse( prettyPrint() ),
                            pathParameters(
                                    parameterWithName("projectKey").description("Key that identifies a group of projects" )
                            ),
                            requestFields(
                                    fieldWithPath( "name" ).description( "The name of the new Term" )
                            )
                        )
                )

    }

}