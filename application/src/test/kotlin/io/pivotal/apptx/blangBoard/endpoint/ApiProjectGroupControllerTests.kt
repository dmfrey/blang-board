package io.pivotal.apptx.blangBoard.endpoint

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.pivotal.apptx.blangBoard.domain.usecases.CreateNewTerm
import io.pivotal.apptx.blangBoard.domain.usecases.responses.TermCreatedResponse
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant
import java.util.*

@RunWith( SpringRunner::class )
@WebMvcTest( ApiProjectGroupController::class )
class ApiProjectGroupControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var mockCreateNewTerm: CreateNewTerm

    @Test
    fun aNewTermIsRecordedForATeamInAProject() {

        val projectKey = "fake-project-key"
        val termUuid = UUID.randomUUID()

        val termCreatedResponse = TermCreatedResponse( termUuid, "fake-term", Instant.now(), projectKey )
        whenever( mockCreateNewTerm.execute( any() ) ).thenReturn( termCreatedResponse )

        val requestBody = "{\"name\": \"new name\"}"

        mvc.perform(
                post( "/api/projectGroups/{project}/terms", "testProject" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( requestBody )
                        .header( HttpHeaders.LOCATION, "/api/projectGroups/${termCreatedResponse.projectKey}/terms/${termCreatedResponse.termUuid}" )
                )
                .andDo( print() )
                .andExpect( status().is2xxSuccessful )

    }

}