package io.pivotal.apptx.blangBoard.endpoint.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties( ignoreUnknown = true )
data class NewTermRequestModel(
    @JsonProperty( "name" ) val name: String
)
